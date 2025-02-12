package com.elogix.api.product.domain.usecase;

import java.util.List;

import com.elogix.api.generics.domain.usecase.GenericNamedUseCase;
import com.elogix.api.product.domain.gateway.ProductTypeGateway;
import com.elogix.api.product.domain.model.ProductType;

public class ProductTypeUseCase
        extends GenericNamedUseCase<ProductType, ProductTypeGateway> {

    public ProductTypeUseCase(ProductTypeGateway gateway) {
        super(gateway);
    }

    public ProductType updateCategory(String category, Long id) {
        return gateway.updateCategory(category, id);
    }

    public ProductType updateIsMeasurable(Long id, boolean isMeasurable) {
        return gateway.updateIsMeasurable(id, isMeasurable);
    }

    public List<ProductType> findByCategory(String name, boolean isDeleted) {
        return gateway.findByCategory(name, isDeleted);
    }
}
