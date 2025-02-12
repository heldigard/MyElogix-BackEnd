package com.elogix.api.delivery_order.infrastructure.repository.delivery_order;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.elogix.api.generics.infrastructure.repository.GenericProduction.GenericProductionRepository;

@Repository
public interface DeliveryOrderDataJpaRepository
                extends GenericProductionRepository<DeliveryOrderData> {
        @Query("""
                        FROM DeliveryOrderData do2
                        WHERE do2.id IN (:idList)
                        AND do2.customer.id = :customerId
                        """)
        Optional<DeliveryOrderData> findByIdListAndCustomerId(List<Long> idList, Long customerId, Sort sort);

        @Query("""
                        FROM DeliveryOrderData do2
                        WHERE do2.customer.id = :customerId
                        AND do2.isBilled = :isBilled
                        AND do2.createdAt BETWEEN :startDate AND :endDate
                        AND do2.status.id IN :statusIdList
                        """)
        List<DeliveryOrderData> getOrdersForCustomerInvoicing(
                        Long customerId,
                        boolean isBilled,
                        Instant startDate,
                        Instant endDate,
                        List<Long> statusIdList,
                        Pageable pageable);
}
