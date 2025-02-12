package com.elogix.api.product.domain.usecase;

import com.elogix.api.product.domain.model.ProductCategory;
import com.elogix.api.product.domain.gateway.ProductCategoryGateway;
import com.elogix.api.generics.domain.usecase.GenericNamedUseCase;

public class ProductCategoryUseCase
        extends GenericNamedUseCase<ProductCategory, ProductCategoryGateway> {

    public ProductCategoryUseCase(ProductCategoryGateway gateway) {
        super(gateway);
    }
}
