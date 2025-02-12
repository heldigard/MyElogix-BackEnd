package com.elogix.api.product.domain.usecase;

import com.elogix.api.generics.domain.usecase.GenericStatusUseCase;
import com.elogix.api.product.domain.gateway.ProductGateway;
import com.elogix.api.product.domain.model.Product;
import com.elogix.api.shared.domain.model.Hits;

public class ProductUseCase
        extends GenericStatusUseCase<Product, ProductGateway> {

    public ProductUseCase(ProductGateway gateway) {
        super(gateway);
    }

    public void deleteByReference(String reference) {
        gateway.deleteByReference(reference);
    }

    public Product findByReference(String reference, boolean isDeleted) {
        return gateway.findByReference(reference, isDeleted);
    }

    public int updateHits(Hits hits) {
        return gateway.updateHits(hits);
    }

    public int incrementHits(Hits hits) {
        return gateway.incrementHits(hits);
    }

    public void incrementOneHit(Long id) {
        gateway.incrementOneHit(id);
    }
}
