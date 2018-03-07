package com.stl.model;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Model class to hold Dividend data
 *
 * @author Jasvinder Singh Saggu
 */
public class Dividend {
    public final BigDecimal lastDividend;
    public final Optional<BigDecimal> fixedDividend;
    public final BigDecimal parValue;

    public Dividend(BigDecimal lastDividend, Optional<BigDecimal> fixedDividend, BigDecimal parValue) {
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }

    @Override
    public String toString() {
        return "Dividend{" +
                "lastDividend=" + lastDividend +
                ", fixedDividend=" + fixedDividend +
                ", parValue=" + parValue +
                '}';
    }
}