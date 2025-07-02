package com.sorting.BackendSortingTool.infrastructure.repository;

import com.sorting.BackendSortingTool.core.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryAdapterTest {
    private ProductJpaRepository jpaRepository;
    private ProductRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        jpaRepository = Mockito.mock(ProductJpaRepository.class);
        adapter = new ProductRepositoryAdapter(jpaRepository);
    }

    @Test
    void findAll_returnsDomainProducts() {
        ProductEntity entity = new ProductEntity();
        entity.setId(1L);
        entity.setName("Test");
        entity.setSalesUnits(100);
        Map<String, Integer> stock = new HashMap<>();
        stock.put("S", 10);
        entity.setStockBySize(stock);
        Mockito.when(jpaRepository.findAll()).thenReturn(Collections.singletonList(entity));
        List<Product> products = adapter.findAll();
        assertEquals(1, products.size());
        Product p = products.get(0);
        assertEquals(1L, p.getId());
        assertEquals("Test", p.getName());
        assertEquals(100, p.getSalesUnits());
        assertEquals(10, p.getStockBySize().get("S"));
    }

    @Test
    void findAll_emptyList() {
        Mockito.when(jpaRepository.findAll()).thenReturn(Collections.emptyList());
        List<Product> products = adapter.findAll();
        assertTrue(products.isEmpty());
    }
}
