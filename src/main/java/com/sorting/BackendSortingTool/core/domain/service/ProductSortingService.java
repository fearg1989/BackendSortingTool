package com.sorting.BackendSortingTool.core.domain.service;

import com.sorting.BackendSortingTool.core.domain.model.Product;
import com.sorting.BackendSortingTool.core.domain.model.SortingCriteria;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductSortingService {
    public List<Product> sortProducts(List<Product> products, Map<String, Double> weights, List<SortingCriteria> criteriaList) {
        return products.stream()
                .sorted(Comparator.comparingDouble(p -> -calculateScore(p, weights, criteriaList)))
                .collect(Collectors.toList());
    }

    private double calculateScore(Product product, Map<String, Double> weights, List<SortingCriteria> criteriaList) {
        return criteriaList.stream()
                .mapToDouble(c -> c.score(product) * weights.getOrDefault(c.getName(), 0.0))
                .sum();
    }
}
