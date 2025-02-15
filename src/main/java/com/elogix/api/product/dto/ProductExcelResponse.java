package com.elogix.api.product.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.elogix.api.product.domain.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductExcelResponse {
    @Builder.Default
    List<Product> products = new ArrayList<>();
    @Builder.Default
    Set<String> errors = new HashSet<>();
}
