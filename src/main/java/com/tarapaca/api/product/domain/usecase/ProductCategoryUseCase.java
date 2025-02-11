package com.tarapaca.api.product.domain.usecase;

import com.tarapaca.api.product.domain.model.ProductCategory;
import com.tarapaca.api.product.domain.gateway.ProductCategoryGateway;
import com.tarapaca.api.generics.domain.usecase.GenericNamedUseCase;

public class ProductCategoryUseCase
        extends GenericNamedUseCase<ProductCategory, ProductCategoryGateway> {

    public ProductCategoryUseCase(ProductCategoryGateway gateway) {
        super(gateway);
    }
}
