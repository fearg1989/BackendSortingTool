package com.sorting.BackendSortingTool.core.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SalesUnitsCriteriaTest {
    @Test
    void score_returnsSalesUnits() {
        SalesUnitsCriteria criteria = new SalesUnitsCriteria();
        Product p = new Product(1L, "Test", 42, null);
        assertEquals(42.0, criteria.score(p));
    }

    @Test
    void getName_returnsCorrectName() {
        SalesUnitsCriteria criteria = new SalesUnitsCriteria();
        assertEquals("salesUnits", criteria.getName());
    }
}
