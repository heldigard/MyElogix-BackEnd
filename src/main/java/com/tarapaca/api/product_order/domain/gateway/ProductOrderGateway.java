package com.tarapaca.api.product_order.domain.gateway;

import java.util.List;

import com.tarapaca.api.generics.domain.gateway.GenericProductionGateway;
import com.tarapaca.api.product_order.domain.model.ProductOrder;

public interface ProductOrderGateway
        extends GenericProductionGateway<ProductOrder> {
    List<ProductOrder> findByDeliveryOrderId(Long orderId, boolean isDeleted);

    void deleteByDeliveryOrderId(Long orderId);

    void incrementProductHit(ProductOrder productOrder);

    boolean hasOrderDetailsChanged(ProductOrder existing, ProductOrder updated);
}
