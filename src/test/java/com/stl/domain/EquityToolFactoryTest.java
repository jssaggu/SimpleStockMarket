package com.stl.domain;

import org.junit.Test;

import static com.stl.domain.EquityToolFactory.toolFor;
import static com.stl.store.Stocks.GIN;
import static com.stl.store.Stocks.POP;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EquityToolFactoryTest {

    @Test
    public void test_Factory_Returning_Valid_Impl() {
        try {
            final EquityTool commonStock = toolFor(POP);
            assertTrue(commonStock instanceof EquityToolCommonStock);

            final EquityTool preferredStock = toolFor(GIN);
            assertTrue(preferredStock instanceof EquityToolPreferredStock);

        } catch (Exception e) {
            fail("Unexpected Error:" + e.getMessage());
        }
    }
}
