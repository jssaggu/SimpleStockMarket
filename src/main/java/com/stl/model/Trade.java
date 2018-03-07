package com.stl.model;

import com.stl.model.enums.OrderType;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Class to represent a trade
 *
 * @author Jasvinder Singh Saggu
 */
public class Trade {
    public final Stock stock;
    public final OrderType orderType;
    public final BigDecimal tradePrice;
    public final int quantity;
    public final Instant tradeTime;

    public Trade(final Stock stock, final OrderType orderType, final BigDecimal tradePrice, final int quantity, final Instant tradeTime) {
        this.stock = stock;
        this.orderType = orderType;
        this.tradePrice = tradePrice;
        this.quantity = quantity;
        this.tradeTime = tradeTime;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "stock=" + stock +
                ", orderType=" + orderType +
                ", tradePrice=" + tradePrice +
                ", quantity=" + quantity +
                ", tradeTime=" + tradeTime +
                '}';
    }
}
