package com.sorting.BackendSortingTool.core.domain.port;

import com.sorting.BackendSortingTool.core.domain.model.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
}
