package com.stl.domain;

import com.stl.lang.InvalidEquityToolQueryException;
import com.stl.model.Stock;
import com.stl.model.enums.StockType;
import org.junit.Test;

import java.math.BigDecimal;

import static com.stl.store.Stocks.*;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;

/**
 * User Story:
 *  For a given stock,
 *      Given any price as input, calculate the dividend yield
 */
public class DividendCalculatorTest {

    @Test
    public void test_For_Common_Stock() {
        //POP
        final BigDecimal popDividendValue = POP.dividendYieldForPrice(valueOf(100.0));
        assertEquals(valueOf(0.08), popDividendValue);

        //ALE
        final BigDecimal aleDividendValue = ALE.dividendYieldForPrice(valueOf(310.0));
        assertEquals(valueOf(0.0742), aleDividendValue);
    }

    @Test
    public void test_For_Preferred_Stock() {
        final BigDecimal ginDividendValue = GIN.dividendYieldForPrice(valueOf(410.0));
        assertEquals(valueOf(0.00488), ginDividendValue);
    }

    @Test(expected = InvalidEquityToolQueryException.class)
    public void test_Negative_For_Common_Stock_With_Zero_Price() {
        POP.dividendYieldForPrice(valueOf(0.0));
    }

    @Test(expected = InvalidEquityToolQueryException.class)
    public void test_Negative_For_Common_Stock_ith_Zero_Devidend() {
        final Stock fooStock = new Stock("FOO", StockType.COMMON, valueOf(10), null);
        fooStock.dividendYieldForPrice(valueOf(10));
    }
}
