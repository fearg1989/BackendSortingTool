package com.sorting.BackendSortingTool.infrastructure.repository;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    private Long id;
    private String name;
    private int salesUnits;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "productentity_stock_by_size")
    @MapKeyColumn(name = "size")
    private Map<String, Integer> stockBySize;
}
