package com.elogix.api.delivery_order.infrastructure.repository.delivery_order_basic;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.elogix.api.delivery_order.domain.gateway.DeliveryOrderBasicGateway;
import com.elogix.api.delivery_order.domain.model.DeliveryOrderBasic;
import com.elogix.api.delivery_order.dto.CustomerOrdersSummaryDTO;
import com.elogix.api.delivery_order.infrastructure.helper.mapper.DeliveryOrderBasicMapper;
import com.elogix.api.generics.infrastructure.dto.PaginationResponse;
import com.elogix.api.generics.infrastructure.repository.GenericBasic.GenericBasicGatewayImpl;
import com.elogix.api.shared.domain.model.pagination.DateRange;
import com.elogix.api.shared.domain.model.pagination.PaginationCriteria;
import com.elogix.api.shared.infraestructure.helpers.RequestUtils;

import jakarta.persistence.EntityManager;

public class DeliveryOrderBasicGatewayImpl
                extends
                GenericBasicGatewayImpl<DeliveryOrderBasic, DeliveryOrderBasicData, DeliveryOrderBasicDataJpaRepository, DeliveryOrderBasicMapper>
                implements DeliveryOrderBasicGateway {
        private final RequestUtils requestUtils;

        public DeliveryOrderBasicGatewayImpl(
                        DeliveryOrderBasicDataJpaRepository repository,
                        DeliveryOrderBasicMapper mapper,
                        EntityManager entityManager,
                        RequestUtils requestUtils,
                        String deletedFilter) {
                super(repository, mapper, entityManager, deletedFilter);
                this.requestUtils = requestUtils;
        }

        @Override
        public List<DeliveryOrderBasic> getOrdersForInvoicing(
                        DateRange dateRange,
                        PaginationCriteria pagination,
                        boolean includeDeleted) {
                List<Long> statusIdList = requestUtils.getBillableDeliveryOrderStatusIds();

                return getByStatusAndDateRange(
                                statusIdList,
                                dateRange,
                                pagination,
                                includeDeleted);
        }

        @Override
        public PaginationResponse<DeliveryOrderBasic> getByStatusFiltered(
                        List<Long> statusIdList,
                        PaginationCriteria pagination,
                        boolean includeDeleted) throws IllegalArgumentException {
                Page<DeliveryOrderBasicData> pageOrdersBasicData;
                try (Session session = setDeleteFilter(includeDeleted)) {
                        Sort sort = getDefaultSort(pagination.getSortOrders());
                        Pageable paging = PageRequest.of(pagination.getPage(), pagination.getPageSize(), sort);

                        pageOrdersBasicData = repository.getByStatusFiltered(
                                        statusIdList,
                                        paging);
                }

                List<DeliveryOrderBasic> items = pageOrdersBasicData.stream()
                                .map(mapper::toDomain)
                                .toList();

                return new PaginationResponse<>(
                                items,
                                pageOrdersBasicData.getTotalElements(),
                                pageOrdersBasicData.getTotalPages(),
                                pageOrdersBasicData.getNumber(),
                                !items.isEmpty());
        }

        @Override
        public List<DeliveryOrderBasic> getByStatusAndDateRange(
                        List<Long> statusIdList,
                        DateRange dateRange,
                        PaginationCriteria pagination,
                        boolean includeDeleted) throws IllegalArgumentException {
                Page<DeliveryOrderBasicData> deliveryOrderBasicDataList;
                try (Session session = setDeleteFilter(includeDeleted)) {
                        Sort sort = getDefaultSort(pagination.getSortOrders());
                        Pageable paging = PageRequest.of(pagination.getPage(), pagination.getPageSize(), sort);

                        deliveryOrderBasicDataList = repository.getByStatusAndDateRange(
                                        statusIdList,
                                        dateRange.startDate(),
                                        dateRange.endDate(),
                                        paging);
                }

                return deliveryOrderBasicDataList.stream()
                                .map(mapper::toDomain)
                                .toList();
        }

        @Override
        public List<CustomerOrdersSummaryDTO> findCustomerOrdersSummary(
                        DateRange dateRange,
                        boolean isBilled,
                        PaginationCriteria pagination,
                        boolean includeDeleted) throws IllegalArgumentException {
                try (Session session = setDeleteFilter(includeDeleted)) {
                        // Pageable paging = PageRequest.of(pagination.getPage(),
                        // pagination.getPageSize());
                        List<Long> statusIdList = requestUtils.getBillableDeliveryOrderStatusIds();

                        Timestamp startTimestamp = Timestamp.from(dateRange.startDate());
                        Timestamp endTimestamp = Timestamp.from(dateRange.endDate());

                        List<CustomerOrdersSummaryDTO> customerOrdersSummary = repository.findCustomerOrdersSummary(
                                        isBilled,
                                        statusIdList,
                                        startTimestamp,
                                        endTimestamp);

                        return customerOrdersSummary;
                }
        }
}
