package com.elogix.api.product.domain.gateway;

import com.elogix.api.generics.domain.gateway.GenericStatusGateway;
import com.elogix.api.product.domain.model.Product;
import com.elogix.api.shared.domain.model.Hits;

public interface ProductGateway
        extends GenericStatusGateway<Product> {
    void deleteByReference(String reference);

    Product findByReference(String reference, boolean isDeleted);

    int updateHits(Hits hits);

    int incrementHits(Hits hits);

    void incrementOneHit(Long id);
}
