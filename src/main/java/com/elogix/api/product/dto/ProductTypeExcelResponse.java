package com.elogix.api.product.dto;

import com.elogix.api.generics.infrastructure.dto.ExcelResponse;
import com.elogix.api.product.domain.model.ProductType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@SuppressWarnings("unchecked")
public class ProductTypeExcelResponse extends ExcelResponse<ProductType> {
}
