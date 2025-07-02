package com.sorting.BackendSortingTool.infrastructure.repository;

import com.sorting.BackendSortingTool.core.domain.model.Product;
import com.sorting.BackendSortingTool.core.domain.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductJpaRepository jpaRepository;

    @Override
    @Cacheable("products")
    public List<Product> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Product toDomain(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getSalesUnits(),
                entity.getStockBySize()
        );
    }
}
