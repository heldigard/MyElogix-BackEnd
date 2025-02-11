package com.tarapaca.api.product.dto;

import com.tarapaca.api.product.infrastructure.repository.product_category.ProductCategoryData;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryExcelResponse {
    @Builder.Default
    List<ProductCategoryData> productCategories = new ArrayList<>();
    @Builder.Default
    Set<String> errors = new HashSet<>();
}
