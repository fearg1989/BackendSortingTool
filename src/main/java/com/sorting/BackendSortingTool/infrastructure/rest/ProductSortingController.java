package com.sorting.BackendSortingTool.infrastructure.rest;

import com.sorting.BackendSortingTool.core.application.SortProductsUseCase;
import com.sorting.BackendSortingTool.core.domain.model.*;
import com.sorting.BackendSortingTool.infrastructure.rest.dto.ProductDTO;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "Product Sorting", description = "APIs for sorting products by weighted criteria")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductSortingController {
    private final SortProductsUseCase sortProductsUseCase;


    @Operation(
        summary = "Sort products by weighted criteria",
        description = "Returns a list of products sorted according to the provided weights for each criterion.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Sorting weights for each criterion",
            required = true,
            content = @Content(
                schema = @Schema(implementation = SortingRequest.class),
                examples = @ExampleObject(
                    name = "Example request",
                    value = "{\n  \"weights\": {\n    \"salesUnits\": 1.0,\n    \"stockRatio\": 0.5\n  }\n}"
                )
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Sorted product list",
                content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)),
                    examples = @ExampleObject(
                        name = "Example response",
                        value = "[\n  {\n    \"id\": 1,\n    \"name\": \"Producto A\",\n    \"salesUnits\": 100,\n    \"stockBySize\": {\"S\": 10, \"M\": 5, \"L\": 2}\n  }\n]"
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Malformed JSON",
                content = @Content(
                    mediaType = "application/json",
                    examples = {
                        @ExampleObject(
                            name = "MalformedJsonRequest",
                            summary = "Request with invalid type for stockRatio",
                            description = "Este request produce un error de parseo JSON.",
                            value = "{\n  \"weights\": {\n    \"salesUnits\": 1,\n    \"stockRatio\": \"f\"\n  }\n}"
                        ),
                        @ExampleObject(
                            name = "MalformedJsonResponse",
                            summary = "Response for malformed JSON input",
                            description = "Respuesta de error cuando el JSON es inválido.",
                            value = "{\n  \"path\": \"uri=/api/products/sort\",\n  \"error\": \"MalformedJson\",\n  \"message\": \"JSON parse error: Unrecognized token 'f': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')\"\n}"
                        )
                    }
                )
            ),
            @ApiResponse(responseCode = "404", description = "Not Found",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Not Found",
                        value = "{\n  \"error\": \"Not Found\",\n  \"message\": \"Resource not found\"\n}"
                    )
                )
            )
        }
    )
    @PostMapping("/sort")
    public ResponseEntity<List<ProductDTO>> sortProducts(@RequestBody(required = false) SortingRequest request) {
        if (request == null || request.getWeights() == null) {
            throw new IllegalArgumentException("Weights must be provided and not null");
        }
        List<SortingCriteria> criteriaList = getCriteriaList();
        List<Product> sorted = sortProductsUseCase.execute(request.getWeights(), criteriaList);
        List<ProductDTO> result = sorted.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    private List<SortingCriteria> getCriteriaList() {
        return Arrays.asList(
                new SalesUnitsCriteria(),
                new StockRatioCriteria()
        );
    }

    private ProductDTO toDTO(Product product) {
        Map<String, Integer> stock = product.getStockBySize();
        LinkedHashMap<String, Integer> orderedStock = new LinkedHashMap<>();
        for (String size : Arrays.asList("S", "M", "L")) {
            if (stock != null && stock.containsKey(size)) {
                orderedStock.put(size, stock.get(size));
            }
        }
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getSalesUnits(),
                orderedStock
        );
    }

    public static class SortingRequest {
        private Map<String, Double> weights;
        public Map<String, Double> getWeights() { return weights; }
        public void setWeights(Map<String, Double> weights) { this.weights = weights; }
    }
}
