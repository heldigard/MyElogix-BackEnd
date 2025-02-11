package com.tarapaca.api.product.domain.gateway;

import java.util.List;

import com.tarapaca.api.generics.domain.gateway.GenericNamedGateway;
import com.tarapaca.api.product.domain.model.ProductType;

public interface ProductTypeGateway
        extends GenericNamedGateway<ProductType> {
    ProductType updateCategory(String name, Long id);

    ProductType updateIsMeasurable(Long id, boolean isMeasurable);

    List<ProductType> findByCategory(String name, boolean isDeleted);
}
