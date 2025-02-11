package com.tarapaca.api.delivery_order.domain.gateway;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.tarapaca.api.delivery_order.domain.model.DeliveryOrder;
import com.tarapaca.api.delivery_order.dto.DeliveryOrderResponse;
import com.tarapaca.api.generics.domain.gateway.GenericProductionGateway;
import com.tarapaca.api.product_order.domain.model.ProductOrder;
import com.tarapaca.api.shared.domain.model.pagination.DateRange;
import com.tarapaca.api.shared.domain.model.pagination.PaginationCriteria;

public interface DeliveryOrderGateway
        extends GenericProductionGateway<DeliveryOrder> {
    DeliveryOrder nuevo(Long customerId);

    DeliveryOrder updateIsBilled(Long id, boolean isBilled);

    List<DeliveryOrder> updateIsBilled(List<Long> idList, boolean isBilled);

    void incrementCustomerHit(Long customerId);

    void incrementProductOrdersHit(List<ProductOrder> productOrders);

    boolean isOrderFinished(Long id);

    boolean isOrderDelivered(Long id);

    List<DeliveryOrderResponse> getOrdersForCustomerInvoicing(
            Long customerId,
            boolean isBilled,
            DateRange dateRange,
            PaginationCriteria pagination,
            boolean includeDeleted);

    List<DeliveryOrderResponse> findByIdListAndDominantCustomer(
            List<Long> idList,
            List<Sort.Order> sortOrders,
            boolean includeDeleted);
}
