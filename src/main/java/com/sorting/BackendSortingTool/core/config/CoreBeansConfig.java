package com.sorting.BackendSortingTool.core.config;

import com.sorting.BackendSortingTool.core.application.SortProductsUseCase;
import com.sorting.BackendSortingTool.core.domain.port.ProductRepository;
import com.sorting.BackendSortingTool.core.domain.service.ProductSortingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreBeansConfig {
    @Bean
    public SortProductsUseCase sortProductsUseCase(ProductRepository productRepository, ProductSortingService sortingService) {
        return new SortProductsUseCase(productRepository, sortingService);
    }
}
