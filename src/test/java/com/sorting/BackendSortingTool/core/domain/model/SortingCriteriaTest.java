package com.sorting.BackendSortingTool.core.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SortingCriteriaTest {
    @Test
    void dummyImplementationWorks() {
        SortingCriteria criteria = new SortingCriteria() {
            public double score(Product p) { return 42.0; }
            public String getName() { return "dummy"; }
        };
        Product p = new Product(1L, "Test", 0, null);
        assertEquals(42.0, criteria.score(p));
        assertEquals("dummy", criteria.getName());
    }
}
