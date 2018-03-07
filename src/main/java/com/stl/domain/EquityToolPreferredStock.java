package com.stl.domain;

import com.stl.model.Dividend;

import java.math.BigDecimal;

import static com.stl.domain.EquityTool.verifyDevidendElseThrowException;
import static com.stl.domain.EquityTool.verifyPositiveNumberElseThrowException;
import static java.math.BigDecimal.valueOf;

/**
 * Provide calculations specific to Preferred Stock
 *
 * @author Jasvinder Singh Saggu
 */
public class EquityToolPreferredStock implements EquityTool {

    @Override
    public BigDecimal dividendYield(final Dividend dividend, final BigDecimal price) {
        verifyPositiveNumberElseThrowException(price, "Price");
        verifyDevidendElseThrowException(dividend);

        //Convert percentage to value
        final BigDecimal fixedDividend = dividend.fixedDividend.orElse(valueOf(1)).divide(valueOf(100), MC);
        return dividend.parValue.multiply(fixedDividend, MC).divide(price, MC);
    }
}