package com.sorting.BackendSortingTool.infrastructure.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private int salesUnits;
    private Map<String, Integer> stockBySize;
}
