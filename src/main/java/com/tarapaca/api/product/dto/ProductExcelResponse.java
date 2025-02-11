package com.tarapaca.api.product.dto;

import com.tarapaca.api.product.infrastructure.repository.product.ProductData;
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
public class ProductExcelResponse {
    @Builder.Default
    List<ProductData> products = new ArrayList<ProductData>();
    @Builder.Default
    Set<String> errors = new HashSet<>();
}
