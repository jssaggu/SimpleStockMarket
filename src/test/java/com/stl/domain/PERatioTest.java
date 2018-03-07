package com.stl.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static com.stl.store.Stocks.GIN;
import static com.stl.store.Stocks.POP;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;

/**
 * For a given stock,
    Given any price as input, calculate the P/E Ratio
 */
public class PERatioTest {

    @Test
    public void test_PE_Ratio_For_Common_Stock() {
        final BigDecimal popPERatio = POP.peRatio(valueOf(100));
        assertEquals(valueOf(12.5), popPERatio);
    }

    @Test
    public void test_PE_Ratio_For_Preferred_Stock() {
        final BigDecimal popPERatio = GIN.peRatio(valueOf(410.0));
        assertEquals(valueOf(51.3), popPERatio);
    }
}
