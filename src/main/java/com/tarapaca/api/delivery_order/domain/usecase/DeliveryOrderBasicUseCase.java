package com.tarapaca.api.delivery_order.domain.usecase;

import java.util.List;

import com.tarapaca.api.delivery_order.domain.gateway.DeliveryOrderBasicGateway;
import com.tarapaca.api.delivery_order.domain.model.DeliveryOrderBasic;
import com.tarapaca.api.delivery_order.dto.CustomerOrdersSummaryDTO;
import com.tarapaca.api.generics.domain.usecase.GenericBasicUseCase;
import com.tarapaca.api.generics.infrastructure.dto.PaginationResponse;
import com.tarapaca.api.shared.domain.model.pagination.DateRange;
import com.tarapaca.api.shared.domain.model.pagination.PaginationCriteria;

public class DeliveryOrderBasicUseCase
        extends GenericBasicUseCase<DeliveryOrderBasic, DeliveryOrderBasicGateway> {

    public DeliveryOrderBasicUseCase(DeliveryOrderBasicGateway gateway) {
        super(gateway);
    }

    public List<DeliveryOrderBasic> getOrdersForInvoicing(
            DateRange dateRange,
            PaginationCriteria pagination,
            boolean includeDeleted) {
        return gateway.getOrdersForInvoicing(
                dateRange,
                pagination,
                includeDeleted);
    }

    public PaginationResponse<DeliveryOrderBasic> getByStatusFiltered(
            List<Long> statusIdList,
            PaginationCriteria pagination,
            boolean includeDeleted) {
        return gateway.getByStatusFiltered(
                statusIdList,
                pagination,
                includeDeleted);
    }

    public List<DeliveryOrderBasic> getByStatusAndDateRange(
            List<Long> statusIdList,
            DateRange dateRange,
            PaginationCriteria pagination,
            boolean includeDeleted) {
        return gateway.getByStatusAndDateRange(
                statusIdList,
                dateRange,
                pagination,
                includeDeleted);
    }

    public List<CustomerOrdersSummaryDTO> findCustomerOrdersSummary(
            DateRange dateRange,
            boolean isBilled,
            PaginationCriteria pagination,
            boolean includeDeleted) {
        return gateway.findCustomerOrdersSummary(
                dateRange,
                isBilled,
                pagination,
                includeDeleted);
    }
}
