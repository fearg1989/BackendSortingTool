package com.sorting.BackendSortingTool.core.application;

import com.sorting.BackendSortingTool.core.domain.model.Product;
import com.sorting.BackendSortingTool.core.domain.model.SortingCriteria;
import com.sorting.BackendSortingTool.core.domain.port.ProductRepository;
import com.sorting.BackendSortingTool.core.domain.service.ProductSortingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class SortProductsUseCaseTest {
    private ProductRepository repository;
    private SortProductsUseCase useCase;

    private ProductSortingService sortingService;
    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ProductRepository.class);
        sortingService = Mockito.mock(ProductSortingService.class);
        useCase = new SortProductsUseCase(repository, sortingService);
    }

    @Test
    void execute_returnsSortedProducts() {
        List<Product> products = Arrays.asList(
                new Product(1L, "A", 1, null),
                new Product(2L, "B", 2, null)
        );
        Map<String, Double> weights = new HashMap<>();
        List<SortingCriteria> criteria = new ArrayList<>();
        Mockito.when(repository.findAll()).thenReturn(products);
        Mockito.when(sortingService.sortProducts(products, weights, criteria)).thenReturn(products);
        List<Product> result = useCase.execute(weights, criteria);
        assertEquals(products, result);
    }

    @Test
    void execute_handlesEmptyList() {
        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());
        Map<String, Double> weights = new HashMap<>();
        List<SortingCriteria> criteria = new ArrayList<>();
        List<Product> result = useCase.execute(weights, criteria);
        assertTrue(result.isEmpty());
    }
}
