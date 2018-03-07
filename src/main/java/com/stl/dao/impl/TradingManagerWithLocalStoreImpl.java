package com.stl.dao.impl;

import com.stl.dao.TradingManager;
import com.stl.lang.InvalidTradeException;
import com.stl.model.Stock;
import com.stl.model.Trade;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.stl.domain.EquityTool.MC;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.util.Collections.unmodifiableList;

/**
 * Trading Manager implementation with local in-memory storage.
 *
 * @author Jasvinder Singh Saggu
 */
public class TradingManagerWithLocalStoreImpl implements TradingManager {

    private final Map<Stock, List<Trade>> tradesStore = new ConcurrentHashMap<>();

    @Override
    public void recordTrade(final Trade trade) {
        verifyValidTradeElseThrowException(trade);

        final Stock stock = trade.stock;
        if (tradesStore.containsKey(stock)) {
            tradesStore.get(stock).add(trade);
        } else {
            final List<Trade> trades = new ArrayList<>();
            trades.add(trade);
            tradesStore.put(stock, trades);
        }
    }

    private void verifyValidTradeElseThrowException(final Trade trade) throws InvalidTradeException {
        if(trade == null || (trade.quantity <= 0 || trade.tradePrice.compareTo(ZERO) <= 0 || trade.tradeTime == null || trade.tradeTime.isAfter(Instant.now()))){
            throw new InvalidTradeException("Quantity, Trade Price and Time must be valid.");
        }
    }

    @Override
    public Optional<List<Trade>> findTradesBy(final Stock stock) {
        return tradesStore.containsKey(stock) ? Optional.of(unmodifiableList(tradesStore.get(stock))) : Optional.empty();
    }

    @Override
    public int tradeStoreSize() {
        return tradesStore.size();
    }

    /**
     * Formula:
     * (Traded Price * Quantity) / Quantity
     * <p>
     * In order to make most of J8 streams this method first defines predicates, and then simply uses those predicates in the trades stream
     */
    @Override
    public BigDecimal calculateAndGetVolumeWeightedStockPrice(final Stock stock, final int durationInMinutes) {
        final List<Trade> trades = findTradesBy(stock).orElse(Collections.emptyList());

        if (trades.isEmpty()) {
            return BigDecimal.ZERO;
        } else {
            final Instant tradesToLimitDuration = Instant.now().minusSeconds(durationInMinutes * 60);

            //Predicate to filter trades matching the duration criteria
            final Predicate<? super Trade> inTime = (Predicate<Trade>) trade -> trade.tradeTime.isAfter(tradesToLimitDuration);

            final List<BigDecimal> quantityTotalList = new ArrayList<>();
            final List<BigDecimal> priceTotalList = new ArrayList<>();

            //Consumer to sum prices and quantities
            Consumer<? super Trade> updateTotal = (Consumer<Trade>) trade -> {
                priceTotalList.add(trade.tradePrice.multiply(valueOf(trade.quantity)));
                quantityTotalList.add(valueOf(trade.quantity));
            };

            //Iterate over all the trades and calculate weighted stock price
            trades.stream().filter(inTime).forEach(updateTotal);

            final BigDecimal priceTotal = priceTotalList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            final BigDecimal quantityTotal = quantityTotalList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            return priceTotal.divide(quantityTotal, MC);
        }
    }
}