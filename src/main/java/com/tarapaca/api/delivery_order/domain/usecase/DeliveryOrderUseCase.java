package com.tarapaca.api.delivery_order.domain.usecase;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.tarapaca.api.delivery_order.domain.gateway.DeliveryOrderGateway;
import com.tarapaca.api.delivery_order.domain.model.DeliveryOrder;
import com.tarapaca.api.delivery_order.dto.DeliveryOrderResponse;
import com.tarapaca.api.generics.domain.usecase.GenericProductionUseCase;
import com.tarapaca.api.product_order.domain.model.ProductOrder;
import com.tarapaca.api.shared.domain.model.pagination.DateRange;
import com.tarapaca.api.shared.domain.model.pagination.PaginationCriteria;

public class DeliveryOrderUseCase
        extends GenericProductionUseCase<DeliveryOrder, DeliveryOrderGateway> {

    public DeliveryOrderUseCase(DeliveryOrderGateway gateway) {
        super(gateway);
    }

    public DeliveryOrder nuevo(Long customerId) {
        return gateway.nuevo(customerId);
    }

    public DeliveryOrder updateIsBilled(Long id, boolean isBilled) {
        return gateway.updateIsBilled(id, isBilled);
    }

    public List<DeliveryOrder> updateIsBilled(List<Long> ids, boolean isBilled) {
        return gateway.updateIsBilled(ids, isBilled);
    }

    public void incrementCustomerHit(Long customerId) {
        gateway.incrementCustomerHit(customerId);
    }

    public void incrementProductOrdersHit(List<ProductOrder> productOrders) {
        gateway.incrementProductOrdersHit(productOrders);
    }

    public boolean isOrderFinished(Long id) {
        return gateway.isOrderFinished(id);
    }

    public boolean isOrderDelivered(Long id) {
        return gateway.isOrderDelivered(id);
    }

    public List<DeliveryOrderResponse> getOrdersForCustomerInvoicing(
            Long customerId,
            boolean isBilled,
            DateRange dateRange,
            PaginationCriteria pagination,
            boolean includeDeleted) {
        return gateway.getOrdersForCustomerInvoicing(
                customerId,
                isBilled,
                dateRange,
                pagination,
                includeDeleted);
    }

    public List<DeliveryOrderResponse> findByIdListAndDominantCustomer(
            List<Long> idList,
            List<Sort.Order> sortOrders,
            boolean includeDeleted) {
        return gateway.findByIdListAndDominantCustomer(idList, sortOrders, includeDeleted);
    }
}
