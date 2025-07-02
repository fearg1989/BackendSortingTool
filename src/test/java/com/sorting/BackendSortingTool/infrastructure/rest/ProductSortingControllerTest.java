package com.sorting.BackendSortingTool.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sorting.BackendSortingTool.core.application.SortProductsUseCase;
import com.sorting.BackendSortingTool.core.domain.model.Product;
import com.sorting.BackendSortingTool.infrastructure.rest.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductSortingController.class)
class ProductSortingControllerTest {
    @Test
    void sortProducts_handlesMissingWeightsField() throws Exception {
        mockMvc.perform(post("/api/products/sort")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void sortProducts_handlesNullRequest() throws Exception {
        mockMvc.perform(post("/api/products/sort")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void sortProducts_handlesProductWithNullStock() throws Exception {
        // Product with stockBySize == null
        Product nullStockProduct = new Product(3L, "Null Stock", 20, null);
        Mockito.when(sortProductsUseCase.execute(Mockito.any(), Mockito.anyList())).thenReturn(Collections.singletonList(nullStockProduct));
        Map<String, Double> weights = new HashMap<>();
        weights.put("salesUnits", 1.0);
        Map<String, Object> request = new HashMap<>();
        request.put("weights", weights);
        mockMvc.perform(post("/api/products/sort")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3L))
                .andExpect(jsonPath("$[0].stockBySize").isEmpty());
    }
    @Test
    void sortProducts_handlesRequestWithNoStockForSizes() throws Exception {
        // Product with only "M" in stock
        Map<String, Integer> stock = new HashMap<>();
        stock.put("M", 7);
        Product onlyM = new Product(2L, "Only M", 50, stock);
        Mockito.when(sortProductsUseCase.execute(Mockito.any(), Mockito.anyList())).thenReturn(Collections.singletonList(onlyM));
        Map<String, Double> weights = new HashMap<>();
        weights.put("salesUnits", 1.0);
        Map<String, Object> request = new HashMap<>();
        request.put("weights", weights);
        mockMvc.perform(post("/api/products/sort")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].stockBySize.M").value(7))
                .andExpect(jsonPath("$[0].stockBySize.S").doesNotExist())
                .andExpect(jsonPath("$[0].stockBySize.L").doesNotExist());
    }
    @Test
    void sortProducts_handlesNullRequestBody() throws Exception {
        mockMvc.perform(post("/api/products/sort")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SortProductsUseCase sortProductsUseCase;
    @Autowired
    private ObjectMapper objectMapper;

    private Product product;

    @BeforeEach
    void setUp() {
        Map<String, Integer> stock = new HashMap<>();
        stock.put("S", 10);
        stock.put("M", 5);
        stock.put("L", 2);
        product = new Product(1L, "Test Product", 100, stock);
    }

    @Test
    void sortProducts_returnsSortedProducts() throws Exception {
        List<Product> products = Collections.singletonList(product);
        Mockito.when(sortProductsUseCase.execute(any(), anyList())).thenReturn(products);
        Map<String, Double> weights = new HashMap<>();
        weights.put("salesUnits", 1.0);
        weights.put("stockRatio", 1.0);
        Map<String, Object> request = new HashMap<>();
        request.put("weights", weights);
        mockMvc.perform(post("/api/products/sort")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].salesUnits").value(100))
                .andExpect(jsonPath("$[0].stockBySize.S").value(10))
                .andExpect(jsonPath("$[0].stockBySize.M").value(5))
                .andExpect(jsonPath("$[0].stockBySize.L").value(2));
    }

    @Test
    void sortProducts_handlesEmptyList() throws Exception {
        Mockito.when(sortProductsUseCase.execute(any(), anyList())).thenReturn(Collections.emptyList());
        Map<String, Double> weights = new HashMap<>();
        Map<String, Object> request = new HashMap<>();
        request.put("weights", weights);
        mockMvc.perform(post("/api/products/sort")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void sortProducts_handlesNullWeights() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("weights", null);
        mockMvc.perform(post("/api/products/sort")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sortProducts_handlesMissingWeights() throws Exception {
        Map<String, Object> request = new HashMap<>();
        mockMvc.perform(post("/api/products/sort")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sortProducts_handlesInvalidFormat() throws Exception {
        String invalidJson = "{\"weights\": \"not_a_map\"}";
        mockMvc.perform(post("/api/products/sort")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sortProducts_handlesIllegalArgumentException() throws Exception {
        Mockito.when(sortProductsUseCase.execute(any(), anyList())).thenThrow(new IllegalArgumentException("Invalid weights"));
        Map<String, Double> weights = new HashMap<>();
        weights.put("salesUnits", 1.0);
        Map<String, Object> request = new HashMap<>();
        request.put("weights", weights);
        mockMvc.perform(post("/api/products/sort")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Invalid weights")));
    }

    @Test
    void sortProducts_handlesUnexpectedException() throws Exception {
        Mockito.when(sortProductsUseCase.execute(any(), anyList())).thenThrow(new RuntimeException("Unexpected error"));
        Map<String, Double> weights = new HashMap<>();
        weights.put("salesUnits", 1.0);
        Map<String, Object> request = new HashMap<>();
        request.put("weights", weights);
        mockMvc.perform(post("/api/products/sort")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Unexpected error")));
    }
}
