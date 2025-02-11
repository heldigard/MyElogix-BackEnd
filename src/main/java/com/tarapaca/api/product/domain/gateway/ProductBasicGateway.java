package com.tarapaca.api.product.domain.gateway;

import com.tarapaca.api.product.domain.model.ProductBasic;
import com.tarapaca.api.generics.domain.gateway.GenericBasicGateway;

public interface ProductBasicGateway
        extends GenericBasicGateway<ProductBasic> {
    ProductBasic findByReference(String reference, boolean isDeleted);
}
