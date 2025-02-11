package com.tarapaca.api.product.domain.gateway;

import com.tarapaca.api.generics.domain.gateway.GenericStatusGateway;
import com.tarapaca.api.product.domain.model.Product;
import com.tarapaca.api.shared.domain.model.Hits;

public interface ProductGateway
        extends GenericStatusGateway<Product> {
    void deleteByReference(String reference);

    Product findByReference(String reference, boolean isDeleted);

    int updateHits(Hits hits);

    int incrementHits(Hits hits);

    void incrementOneHit(Long id);
}
