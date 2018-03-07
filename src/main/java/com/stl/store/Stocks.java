package com.stl.store;

import com.stl.model.Dividend;
import com.stl.model.Stock;
import com.stl.model.enums.StockType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.util.Optional.empty;

/**
 * Local store to represent a list of available Stocks
 *
 * @author Jasvinder Singh Saggu
 */
public interface Stocks {
    Stock TEA = new Stock("TEA", StockType.COMMON, valueOf(100.0), new Dividend(ZERO, empty(), valueOf(100)));
    Stock POP = new Stock("POP", StockType.COMMON, valueOf(210.10), new Dividend(valueOf(8), empty(), valueOf(100)));
    Stock ALE = new Stock("ALE", StockType.COMMON, valueOf(310.0), new Dividend(valueOf(23), empty(), valueOf(60)));
    Stock GIN = new Stock("GIN", StockType.PREFERRED, valueOf(410.0), new Dividend(valueOf(8), Optional.of(valueOf(2)), valueOf(100)));
    Stock JOE = new Stock("JOE", StockType.COMMON, valueOf(510.55), new Dividend(valueOf(13), empty(), valueOf(250)));

    static List<Stock> stocksList() {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(TEA);
        stocks.add(POP);
        stocks.add(ALE);
        stocks.add(GIN);
        stocks.add(JOE);
        return Collections.unmodifiableList(stocks);
    }
}
