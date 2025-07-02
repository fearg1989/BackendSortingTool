package com.sorting.BackendSortingTool.core.domain.model;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void gettersAndSetters() {
        Map<String, Integer> stock = new HashMap<>();
        stock.put("S", 10);
        Product p = new Product(1L, "Test", 100, stock);
        assertEquals(1L, p.getId());
        assertEquals("Test", p.getName());
        assertEquals(100, p.getSalesUnits());
        assertEquals(10, p.getStockBySize().get("S"));
    }

    @Test
    void stockBySize_nullSafe() {
        Product p = new Product(2L, "NoStock", 0, null);
        assertNull(p.getStockBySize());
    }
}
