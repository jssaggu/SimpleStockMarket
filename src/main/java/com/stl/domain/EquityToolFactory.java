package com.stl.domain;

import com.stl.model.Stock;

/**
 * Factory to provide correct implmentation nased on stock type
 *
 * @author Jasvinder Singh Saggu
 */
public class EquityToolFactory {

    public static EquityTool toolFor(final Stock stock) {
        switch (stock.type) {
            case COMMON:
                return new EquityToolCommonStock();
            case PREFERRED:
                return new EquityToolPreferredStock();
            default:
                throw new RuntimeException(stock.type + " Not found");
        }
    }
}
