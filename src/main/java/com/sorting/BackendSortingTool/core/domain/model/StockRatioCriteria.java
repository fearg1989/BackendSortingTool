package com.sorting.BackendSortingTool.core.domain.model;

public class StockRatioCriteria implements SortingCriteria {
    @Override
    public double score(Product product) {
        if (product.getStockBySize() == null || product.getStockBySize().isEmpty()) return 0;
        return product.getStockBySize().values().stream().mapToInt(Integer::intValue).sum();
    }
    @Override
    public String getName() {
        return "stockRatio";
    }
}
