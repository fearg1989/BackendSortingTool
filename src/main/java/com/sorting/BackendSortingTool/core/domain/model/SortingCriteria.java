package com.sorting.BackendSortingTool.core.domain.model;

public interface SortingCriteria {
    double score(Product product);
    String getName();
}
