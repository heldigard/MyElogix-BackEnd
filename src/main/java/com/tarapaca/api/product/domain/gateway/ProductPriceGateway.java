package com.tarapaca.api.product.domain.gateway;

import com.tarapaca.api.product.domain.model.ProductPrice;
import com.tarapaca.api.generics.domain.gateway.GenericGateway;

public interface ProductPriceGateway
        extends GenericGateway<ProductPrice> {
    void deleteByProductRef(String productRef);

    ProductPrice findByProductRef(String productRef, boolean isDeleted);
}
