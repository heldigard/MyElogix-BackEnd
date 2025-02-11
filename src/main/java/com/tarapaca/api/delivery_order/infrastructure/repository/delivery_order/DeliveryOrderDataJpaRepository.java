package com.tarapaca.api.delivery_order.infrastructure.repository.delivery_order;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tarapaca.api.generics.infrastructure.repository.GenericProduction.GenericProductionRepository;

@Repository
public interface DeliveryOrderDataJpaRepository
                extends GenericProductionRepository<DeliveryOrderData> {
        @Query("SELECT d FROM DeliveryOrderData d WHERE d.id IN (:idList) and d.customer.id = :customerId")
        Optional<DeliveryOrderData> findByIdListAndCustomerId(List<Long> idList, Long customerId, Sort sort);

        @Query("""
                        SELECT d FROM DeliveryOrderData d
                        WHERE d.customer.id = :customerId
                        AND d.isBilled = :isBilled
                        AND d.createdAt BETWEEN :startDate AND :endDate
                        AND d.status.id IN :statusIdList
                        """)
        List<DeliveryOrderData> getOrdersForCustomerInvoicing(
                        Long customerId,
                        boolean isBilled,
                        Instant startDate,
                        Instant endDate,
                        List<Long> statusIdList,
                        Pageable pageable);
}
