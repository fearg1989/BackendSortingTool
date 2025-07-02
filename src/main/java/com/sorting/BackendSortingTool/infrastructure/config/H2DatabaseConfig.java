package com.sorting.BackendSortingTool.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.sorting.BackendSortingTool.infrastructure.repository")
public class H2DatabaseConfig {
}
