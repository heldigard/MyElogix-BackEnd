package com.elogix.api.product.domain.usecase;

import com.elogix.api.product.domain.model.ProductBasic;
import com.elogix.api.product.domain.gateway.ProductBasicGateway;
import com.elogix.api.generics.domain.usecase.GenericBasicUseCase;

public class ProductBasicUseCase
        extends GenericBasicUseCase<ProductBasic, ProductBasicGateway> {
    private final ProductBasicGateway gateway;

    public ProductBasicUseCase(ProductBasicGateway gateway) {
        super(gateway);
        this.gateway = gateway;
    }

    public ProductBasic findByReference(String reference, boolean isDeleted) {
        return gateway.findByReference(reference, isDeleted);
    }
}
