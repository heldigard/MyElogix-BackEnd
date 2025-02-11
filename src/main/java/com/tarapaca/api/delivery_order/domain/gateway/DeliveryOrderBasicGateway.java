package com.tarapaca.api.delivery_order.domain.gateway;

import java.util.List;

import com.tarapaca.api.delivery_order.domain.model.DeliveryOrderBasic;
import com.tarapaca.api.delivery_order.dto.CustomerOrdersSummaryDTO;
import com.tarapaca.api.generics.domain.gateway.GenericBasicGateway;
import com.tarapaca.api.generics.infrastructure.dto.PaginationResponse;
import com.tarapaca.api.shared.domain.model.pagination.DateRange;
import com.tarapaca.api.shared.domain.model.pagination.PaginationCriteria;

public interface DeliveryOrderBasicGateway
                extends GenericBasicGateway<DeliveryOrderBasic> {

        List<DeliveryOrderBasic> getOrdersForInvoicing(
                        DateRange dateRange,
                        PaginationCriteria pagination,
                        boolean includeDeleted);

        PaginationResponse<DeliveryOrderBasic> getByStatusFiltered(
                        List<Long> statusIdList,
                        PaginationCriteria pagination,
                        boolean includeDeleted);

        List<DeliveryOrderBasic> getByStatusAndDateRange(
                        List<Long> statusIdList,
                        DateRange dateRange,
                        PaginationCriteria pagination,
                        boolean includeDeleted);

        List<CustomerOrdersSummaryDTO> findCustomerOrdersSummary(
                        DateRange dateRange,
                        boolean isBilled,
                        PaginationCriteria pagination,
                        boolean includeDeleted);
}
