package com.tarapaca.api.product.dto;

import com.tarapaca.api.product.infrastructure.repository.product_type.ProductTypeData;
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
public class ProductTypeExcelResponse {
    @Builder.Default
    List<ProductTypeData> productTypes = new ArrayList<ProductTypeData>();
    @Builder.Default
    Set<String> errors = new HashSet<>();
}
