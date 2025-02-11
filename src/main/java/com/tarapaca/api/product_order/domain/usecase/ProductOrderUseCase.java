package com.tarapaca.api.product_order.domain.usecase;

import java.util.List;

import org.springframework.lang.NonNull;

import com.tarapaca.api.generics.domain.usecase.GenericProductionUseCase;
import com.tarapaca.api.product_order.domain.gateway.ProductOrderGateway;
import com.tarapaca.api.product_order.domain.model.ProductOrder;

public class ProductOrderUseCase
        extends GenericProductionUseCase<ProductOrder, ProductOrderGateway> {

    public ProductOrderUseCase(ProductOrderGateway gateway) {
        super(gateway);
    }

    public List<ProductOrder> findByDeliveryOrderId(Long orderId, boolean isDeleted) {
        return gateway.findByDeliveryOrderId(orderId, isDeleted);
    }

    public void deleteByDeliveryOrderId(Long orderId) {
        gateway.deleteByDeliveryOrderId(orderId);
    }

    public void incrementProductHit(ProductOrder productOrder) {
        gateway.incrementProductHit(productOrder);
    }

    public boolean hasOrderDetailsChanged(@NonNull ProductOrder existing, @NonNull ProductOrder updated) {
        return gateway.hasOrderDetailsChanged(existing, updated);
    }
}
