package com.stl.model;

import com.stl.model.enums.StockType;

import java.math.BigDecimal;
import java.util.Objects;

import static com.stl.domain.EquityToolFactory.toolFor;

/**
 * Stock model class to hold stock related information
 * <p>
 * It's assumed that once class is initialise Symbol name and Type can not be changed. However current Price and Dividend are tend to change.
 * <p>
 * This class also provides helper methods to get Dividend and PE/Ratio values
 *
 * @author Jasvinder Singh Saggu
 */
public class Stock {
    public final String symbol;
    public final StockType type;
    private BigDecimal currentPrice;
    private Dividend dividend;

    public Stock(final String symbol, final StockType type) {
        this.symbol = symbol;
        this.type = type;
    }

    public Stock(final String symbol, final StockType type, final BigDecimal currentPrice, final Dividend dividend) {
        this.symbol = symbol;
        this.type = type;
        this.currentPrice = currentPrice;
        this.dividend = dividend;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(final BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Dividend getDividend() {
        return dividend;
    }

    public void setDividend(Dividend dividend) {
        this.dividend = dividend;
    }

    public BigDecimal dividendYieldForPrice(final BigDecimal price) {
        return toolFor(this).dividendYield(dividend, price);
    }

    public BigDecimal peRatio(final BigDecimal price) {
        return toolFor(this).peRatio(dividend.lastDividend, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(symbol, stock.symbol);
    }

    @Override
    public int hashCode() {

        return Objects.hash(symbol);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", type=" + type +
                ", currentPrice=" + currentPrice +
                ", dividend=" + dividend +
                '}';
    }
}