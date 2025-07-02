package com.sorting.BackendSortingTool.core.application;

import com.sorting.BackendSortingTool.core.domain.model.Product;
import com.sorting.BackendSortingTool.core.domain.model.SortingCriteria;
import com.sorting.BackendSortingTool.core.domain.port.ProductRepository;
import com.sorting.BackendSortingTool.core.domain.service.ProductSortingService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SortProductsUseCase {
    private final ProductRepository productRepository;
    private final ProductSortingService sortingService;

    public List<Product> execute(Map<String, Double> weights, List<SortingCriteria> criteriaList) {
        List<Product> products = productRepository.findAll();
        return sortingService.sortProducts(products, weights, criteriaList);
    }
}
