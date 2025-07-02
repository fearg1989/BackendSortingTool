package com.sorting.BackendSortingTool.core.domain.model;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class StockRatioCriteriaTest {
    @Test
    void score_returnsStockSum() {
        StockRatioCriteria criteria = new StockRatioCriteria();
        Map<String, Integer> stock = new HashMap<>();
        stock.put("S", 2);
        stock.put("M", 3);
        stock.put("L", 5);
        Product p = new Product(1L, "Test", 0, stock);
        assertEquals(10.0, criteria.score(p));
    }

    @Test
    void score_handlesNullStock() {
        StockRatioCriteria criteria = new StockRatioCriteria();
        Product p = new Product(1L, "Test", 0, null);
        assertEquals(0.0, criteria.score(p));
    }

    @Test
    void score_handlesEmptyStock() {
        StockRatioCriteria criteria = new StockRatioCriteria();
        Product p = new Product(1L, "Test", 0, Collections.emptyMap());
        assertEquals(0.0, criteria.score(p));
    }

    @Test
    void getName_returnsCorrectName() {
        StockRatioCriteria criteria = new StockRatioCriteria();
        assertEquals("stockRatio", criteria.getName());
    }
}
