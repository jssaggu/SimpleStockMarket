package com.stl.dao;


import com.stl.dao.impl.TradingManagerWithLocalStoreImpl;
import com.stl.lang.InvalidTradeException;
import com.stl.model.Trade;
import org.junit.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.stl.model.enums.OrderType.BUY;
import static com.stl.model.enums.OrderType.SELL;
import static com.stl.store.Stocks.*;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.time.Instant.now;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * User Stories:
 * A. For a given stock,
 *   i. Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price
 *  ii. Calculate Volume Weighted Stock Price based on trades in past 15 minutes
 * B. Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
 */
public class TradingTest {
    @Test
    public void test_New_Trades_Can_Be_Registered() {
        final TradingManager manager = new TradingManagerWithLocalStoreImpl();
        manager.recordTrade(new Trade(TEA, BUY, valueOf(10.5), 100, now()));
        manager.recordTrade(new Trade(TEA, SELL, valueOf(11), 50, now()));
        manager.recordTrade(new Trade(POP, BUY, valueOf(110), 50, now()));
        manager.recordTrade(new Trade(JOE, BUY, valueOf(110), 50, now()));

        assertEquals(3, manager.tradeStoreSize());
    }

    @Test
    public void test_Registered_Trades_Can_Be_Found() {
        final TradingManager manager = new TradingManagerWithLocalStoreImpl();
        manager.recordTrade(new Trade(TEA, BUY, valueOf(10.5), 100, now()));
        manager.recordTrade(new Trade(TEA, SELL, valueOf(11), 50, now()));
        manager.recordTrade(new Trade(POP, BUY, valueOf(110), 50, now()));

        final Optional<List<Trade>> findTEA = manager.findTradesBy(TEA);

        assertEquals(Integer.valueOf(2), findTEA.map(List::size).orElse(0));

        //Get first trade from the found trades
        final Trade teaTrade = findTEA.get().get(0);
        assertEquals(TEA.symbol, teaTrade.stock.symbol);
        assertEquals(TEA.type, teaTrade.stock.type);
        assertEquals(valueOf(10.5), teaTrade.tradePrice);
        assertEquals(100, teaTrade.quantity);
    }

    @Test
    public void test_Not_Registered_Trades_Can_Not_Be_Found() {
        final TradingManager manager = new TradingManagerWithLocalStoreImpl();
        manager.recordTrade(new Trade(TEA, BUY, valueOf(10.5), 100, now()));

        final Optional<List<Trade>> findTEA = manager.findTradesBy(GIN);

        assertFalse(findTEA.isPresent());
    }

    @Test(expected = InvalidTradeException.class)
    public void test_Invalid_Trade_Is_Failing_ZERO_Price(){
        final TradingManager manager = new TradingManagerWithLocalStoreImpl();
        manager.recordTrade(new Trade(TEA, BUY, ZERO, 100, now()));
    }

    @Test(expected = InvalidTradeException.class)
    public void test_Invalid_Trade_Is_Failing_ZERO_Quantity(){
        final TradingManager manager = new TradingManagerWithLocalStoreImpl();
        manager.recordTrade(new Trade(TEA, BUY, valueOf(100), 0, now()));
    }

    @Test(expected = InvalidTradeException.class)
    public void test_Invalid_Trade_Is_Failing_No_Time(){
        final TradingManager manager = new TradingManagerWithLocalStoreImpl();
        manager.recordTrade(new Trade(TEA, BUY, valueOf(100), 10, null));
    }

    @Test(expected = InvalidTradeException.class)
    public void test_Invalid_Trade_Is_Failing_Trade_Time_In_Future(){
        final TradingManager manager = new TradingManagerWithLocalStoreImpl();
        manager.recordTrade(new Trade(TEA, BUY, valueOf(100), 10, now().plusSeconds(60)));
    }

    @Test
    public void test_Volume_Weighted_Stock_Price(){
        final TradingManager manager = new TradingManagerWithLocalStoreImpl();
        manager.recordTrade(new Trade(TEA, BUY, valueOf(100), 1000, now()));
        manager.recordTrade(new Trade(TEA, SELL, valueOf(101), 500, now()));
        manager.recordTrade(new Trade(TEA, SELL, valueOf(104), 480, now()));
        manager.recordTrade(new Trade(TEA, SELL, valueOf(100), 50, now()));
        manager.recordTrade(new Trade(TEA, SELL, valueOf(101), 10, now()));
        manager.recordTrade(new Trade(POP, BUY, valueOf(210), 500, now()));

        assertEquals(2, manager.tradeStoreSize());
        assertEquals(5, manager.findTradesBy(TEA).get().size());

        assertEquals(valueOf(101), manager.calculateAndGetVolumeWeightedStockPrice(TEA, 15));
    }

    /**
     * Same as normal volume weighted stock price test above except it has couple of extra trades from more than 15mins ago which should not affect the result.
     */
    @Test
    public void test_Volume_Weighted_Stock_Price_Within_Last_15mins(){
        final TradingManager manager = new TradingManagerWithLocalStoreImpl();
        manager.recordTrade(new Trade(TEA, SELL, valueOf(111), 100, now().minusSeconds(25*60)));
        manager.recordTrade(new Trade(TEA, SELL, valueOf(120), 500, now().minusSeconds(16*60)));
        manager.recordTrade(new Trade(TEA, BUY, valueOf(100), 1000, now()));
        manager.recordTrade(new Trade(TEA, SELL, valueOf(101), 500, now()));
        manager.recordTrade(new Trade(TEA, SELL, valueOf(104), 480, now()));
        manager.recordTrade(new Trade(TEA, SELL, valueOf(100), 50, now()));
        manager.recordTrade(new Trade(TEA, SELL, valueOf(101), 10, now()));
        manager.recordTrade(new Trade(POP, BUY, valueOf(210), 500, now()));
        manager.recordTrade(new Trade(POP, BUY, valueOf(210), 500, Instant.now().minusSeconds(28*60)));
        manager.recordTrade(new Trade(POP, BUY, valueOf(210), 500, Instant.now()));

        assertEquals(2, manager.tradeStoreSize());

        assertEquals(valueOf(101), manager.calculateAndGetVolumeWeightedStockPrice(TEA, 15));
    }

    @Test
    public void test_calculate_GBCE_All_Share_Index(){
        assertEquals(valueOf(267.252623), TradingManager.calculateAndGetGBCEAllShareIndex());
    }
}