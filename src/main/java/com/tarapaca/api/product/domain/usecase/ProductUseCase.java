package com.tarapaca.api.product.domain.usecase;

import com.tarapaca.api.generics.domain.usecase.GenericStatusUseCase;
import com.tarapaca.api.product.domain.gateway.ProductGateway;
import com.tarapaca.api.product.domain.model.Product;
import com.tarapaca.api.shared.domain.model.Hits;

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
