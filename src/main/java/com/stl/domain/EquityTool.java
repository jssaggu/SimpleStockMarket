package com.stl.domain;

import com.stl.lang.InvalidEquityToolQueryException;
import com.stl.model.Dividend;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static java.math.BigDecimal.ZERO;

/**
 * Equity Tool interface defines various methods responsible to execute business logic.
 * <p>
 * All the methods specific to stock type should be implemented in respective classes
 *
 * @author Jasvinder Singh Saggu
 */
public interface EquityTool {
    MathContext MC = new MathContext(3, RoundingMode.HALF_UP);

    BigDecimal dividendYield(final Dividend dividend, final BigDecimal price);

    default BigDecimal peRatio(final BigDecimal dividend, final BigDecimal price) {
        return dividend.compareTo(ZERO) > 0 ? price.divide(dividend, MC) : ZERO;
    }

    static void verifyPositiveNumberElseThrowException(final BigDecimal number, final String message) {
        if (number == null || number.compareTo(ZERO) <= 0)
            throw new InvalidEquityToolQueryException(message + " must be non zero.");
    }

    static void verifyDevidendElseThrowException(final Dividend dividend) {
        if (dividend == null)
            throw new InvalidEquityToolQueryException("Dividend values not available.");
    }
}