package com.tarapaca.api.product.domain.usecase;

import com.tarapaca.api.product.domain.model.ProductPrice;
import com.tarapaca.api.product.domain.gateway.ProductPriceGateway;
import com.tarapaca.api.generics.domain.usecase.GenericUseCase;

public class ProductPriceUseCase
        extends GenericUseCase<ProductPrice, ProductPriceGateway> {
    private final ProductPriceGateway gateway;

    public ProductPriceUseCase(ProductPriceGateway gateway) {
        super(gateway);
        this.gateway = gateway;
    }

    public void deleteByProductRef(String productRef) {
        gateway.deleteByProductRef(productRef);
    }

    public ProductPrice findByProductRef(String productRef, boolean isDeleted) {
        return gateway.findByProductRef(productRef, isDeleted);
    }
}
