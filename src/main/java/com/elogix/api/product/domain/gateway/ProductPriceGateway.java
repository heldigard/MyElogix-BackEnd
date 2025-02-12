package com.elogix.api.product.domain.gateway;

import com.elogix.api.product.domain.model.ProductPrice;
import com.elogix.api.generics.domain.gateway.GenericGateway;

public interface ProductPriceGateway
        extends GenericGateway<ProductPrice> {
    void deleteByProductRef(String productRef);

    ProductPrice findByProductRef(String productRef, boolean isDeleted);
}
