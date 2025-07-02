package com.sorting.BackendSortingTool.infrastructure.rest.dto;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {
    @Test
    void stockBySizeHandlesNull() {
        ProductDTO dto = new ProductDTO();
        dto.setStockBySize(null);
        Map<String, Integer> stock = dto.getStockBySize();
        assertNull(stock);
    }

    @Test
    void stockBySizeHandlesEmpty() {
        ProductDTO dto = new ProductDTO();
        dto.setStockBySize(Collections.emptyMap());
        Map<String, Integer> stock = dto.getStockBySize();
        assertTrue(stock.isEmpty());
    }

    @Test
    void stockBySizeHandlesPartialSizes() {
        ProductDTO dto = new ProductDTO();
        Map<String, Integer> stock = new HashMap<>();
        stock.put("M", 5);
        dto.setStockBySize(stock);
        Map<String, Integer> result = dto.getStockBySize();
        assertEquals(1, result.size());
        assertTrue(result.containsKey("M"));
    }

    @Test
    void stockBySizeHandlesAllSizes() {
        ProductDTO dto = new ProductDTO();
        Map<String, Integer> stock = new HashMap<>();
        stock.put("S", 1);
        stock.put("M", 2);
        stock.put("L", 3);
        dto.setStockBySize(stock);
        Map<String, Integer> result = dto.getStockBySize();
        assertEquals(3, result.size());
        assertEquals(1, result.get("S"));
        assertEquals(2, result.get("M"));
        assertEquals(3, result.get("L"));
    }
}
