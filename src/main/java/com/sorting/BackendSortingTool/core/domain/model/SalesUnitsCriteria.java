package com.sorting.BackendSortingTool.core.domain.model;

public class SalesUnitsCriteria implements SortingCriteria {
    @Override
    public double score(Product product) {
        return product.getSalesUnits();
    }
    @Override
    public String getName() {
        return "salesUnits";
    }
}
