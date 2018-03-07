package com.stl.domain;

import com.stl.model.Dividend;

import java.math.BigDecimal;

import static com.stl.domain.EquityTool.verifyDevidendElseThrowException;
import static com.stl.domain.EquityTool.verifyPositiveNumberElseThrowException;
import static java.math.BigDecimal.ZERO;

/**
 * Provide calculations specific to Common Stock
 *
 * @author Jasvinder Singh Saggu
 */
public class EquityToolCommonStock implements EquityTool {
    @Override
    public BigDecimal dividendYield(final Dividend dividend, BigDecimal price) {
        verifyPositiveNumberElseThrowException(price, "Price");
        verifyDevidendElseThrowException(dividend);
        return dividend.lastDividend.compareTo(ZERO) > 0 ? dividend.lastDividend.divide(price, MC) : ZERO;
    }
}
