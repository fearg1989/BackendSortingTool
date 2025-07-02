package com.sorting.BackendSortingTool.infrastructure.config;

import com.sorting.BackendSortingTool.infrastructure.repository.ProductEntity;
import com.sorting.BackendSortingTool.infrastructure.repository.ProductJpaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final ProductJpaRepository productJpaRepository;

    @PostConstruct
    public void init() {
        productJpaRepository.deleteAll();
        List<ProductEntity> products = new ArrayList<>();
        products.add(new ProductEntity(1L, "V-NECH BASIC SHIRT", 100, Map.of("S", 4, "M", 9, "L", 0)));
        products.add(new ProductEntity(2L, "CONTRASTING FABRIC T-SHIRT", 50, Map.of("S", 35, "M", 9, "L", 9)));
        products.add(new ProductEntity(3L, "RAISED PRINT T-SHIRT", 80, Map.of("S", 20, "M", 2, "L", 20)));
        products.add(new ProductEntity(4L, "PLEATED T-SHIRT", 3, Map.of("S", 25, "M", 30, "L", 10)));
        products.add(new ProductEntity(5L, "CONTRASTING LACE T-SHIRT", 650, Map.of("S", 0, "M", 1, "L", 0)));
        products.add(new ProductEntity(6L, "SLOGAN T-SHIRT", 20, Map.of("S", 9, "M", 2, "L", 5)));
        productJpaRepository.saveAll(products);
    }
}
