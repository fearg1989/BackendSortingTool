package com.sorting.BackendSortingTool.core.domain.service;

import com.sorting.BackendSortingTool.core.domain.model.Product;
import com.sorting.BackendSortingTool.core.domain.model.SortingCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductSortingServiceTest {
    private ProductSortingService service;
    private List<Product> products;
    private Map<String, Double> weights;
    private List<SortingCriteria> criteriaList;

    @BeforeEach
    void setUp() {
        service = new ProductSortingService();
        Map<String, Integer> stock1 = new HashMap<>();
        stock1.put("S", 10); stock1.put("M", 5); stock1.put("L", 2);
        Map<String, Integer> stock2 = new HashMap<>();
        stock2.put("S", 1); stock2.put("M", 2); stock2.put("L", 3);
        products = Arrays.asList(
                new Product(1L, "A", 100, stock1),
                new Product(2L, "B", 50, stock2)
        );
        weights = new HashMap<>();
        weights.put("salesUnits", 1.0);
        weights.put("stockRatio", 1.0);
        criteriaList = Arrays.asList(
                new SortingCriteria() {
                    public String getName() { return "salesUnits"; }
                    public double score(Product p) { return p.getSalesUnits(); }
                },
                new SortingCriteria() {
                    public String getName() { return "stockRatio"; }
                    public double score(Product p) { return p.getStockBySize().values().stream().mapToInt(i -> i).sum(); }
                }
        );
    }

    @Test
    void sortProducts_sortsByScoreDescending() {
        List<Product> sorted = service.sortProducts(products, weights, criteriaList);
        assertEquals(1L, sorted.get(0).getId());
        assertEquals(2L, sorted.get(1).getId());
    }

    @Test
    void sortProducts_handlesEmptyList() {
        List<Product> sorted = service.sortProducts(Collections.emptyList(), weights, criteriaList);
        assertTrue(sorted.isEmpty());
    }

    @Test
    void sortProducts_handlesZeroWeights() {
        Map<String, Double> zeroWeights = new HashMap<>();
        List<Product> sorted = service.sortProducts(products, zeroWeights, criteriaList);
        assertEquals(2, sorted.size());
    }

    @Test
    void sortProducts_handlesNullWeights() {
        assertThrows(NullPointerException.class, () -> service.sortProducts(products, null, criteriaList));
    }

    @Test
    void sortProducts_handlesNullCriteriaList() {
        assertThrows(NullPointerException.class, () -> service.sortProducts(products, weights, null));
    }
}
