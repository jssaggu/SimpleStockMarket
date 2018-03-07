package com.stl.dao;

import com.stl.model.Stock;
import com.stl.model.Trade;
import com.stl.store.Stocks;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.stl.domain.EquityTool.MC;
import static java.math.BigDecimal.valueOf;

/**
 * Define all the trading related methods.
 * The methods which need to persist the data must be implemented, else they can be implemented here itself.
 *
 * @author Jasvinder Singh Saggu
 */
public interface TradingManager {

    /**
     * Persist trade in the database
     */
    void recordTrade(final Trade trade);

    Optional<List<Trade>> findTradesBy(Stock stock);

    int tradeStoreSize();

    /**
     * Calculate Volume Weighted Stock Price based on trades in past X minutes
     */
    BigDecimal calculateAndGetVolumeWeightedStockPrice(Stock stock, int durationInMinutes);

    /**
     * Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
     */
    //TODO - Improve algo
    static BigDecimal calculateAndGetGBCEAllShareIndex(){
        BigDecimal accumulate = BigDecimal.ONE;

        for (Stock stock : Stocks.stocksList()) {
            accumulate = accumulate.multiply(stock.getCurrentPrice());
        }

        BigDecimal index = accumulate.divide(accumulate, MC);
        BigDecimal temp;
        BigDecimal e = new BigDecimal("0.1");

        final int totalStocks = Stocks.stocksList().size();
        do {
            temp = index;
            index = index.add(accumulate.subtract(index.pow(totalStocks)).divide(valueOf(totalStocks).multiply(index.pow(totalStocks - 1)), MC));
        } while (index.subtract(temp).abs().compareTo(e) > 0);

        return index;
    }
}