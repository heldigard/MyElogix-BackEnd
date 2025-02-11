package com.tarapaca.api.product.domain.usecase;

import java.util.List;

import com.tarapaca.api.generics.domain.usecase.GenericNamedUseCase;
import com.tarapaca.api.product.domain.gateway.ProductTypeGateway;
import com.tarapaca.api.product.domain.model.ProductType;

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
