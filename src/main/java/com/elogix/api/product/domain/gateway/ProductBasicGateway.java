package com.elogix.api.product.domain.gateway;

import com.elogix.api.product.domain.model.ProductBasic;
import com.elogix.api.generics.domain.gateway.GenericBasicGateway;

public interface ProductBasicGateway
        extends GenericBasicGateway<ProductBasic> {
    ProductBasic findByReference(String reference, boolean isDeleted);
}
