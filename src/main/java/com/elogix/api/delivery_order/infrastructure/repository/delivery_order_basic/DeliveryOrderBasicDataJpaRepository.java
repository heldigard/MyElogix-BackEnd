package com.elogix.api.delivery_order.infrastructure.repository.delivery_order_basic;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elogix.api.delivery_order.dto.CustomerOrdersSummaryDTO;
import com.elogix.api.generics.infrastructure.repository.GenericBasic.GenericBasicRepository;

@Repository
public interface DeliveryOrderBasicDataJpaRepository
                extends GenericBasicRepository<DeliveryOrderBasicData> {
        @Query("""
                        FROM DeliveryOrderBasicData do2
                        WHERE do2.status.id IN (:statusIdList)
                        """)
        Page<DeliveryOrderBasicData> getByStatusFiltered(List<Long> statusIdList, Pageable pageable);

        @Query("""
                        FROM DeliveryOrderBasicData do2
                        WHERE do2.status.id IN (:statusIdList)
                        AND do2.createdAt BETWEEN :startDate AND :endDate
                        """)
        Page<DeliveryOrderBasicData> getByStatusAndDateRange(
                        List<Long> statusIdList,
                        Instant startDate,
                        Instant endDate,
                        Pageable pageable);

        @Query(nativeQuery = true, name = "DeliveryOrderBasicData.findCustomerOrdersSummary", value = """
                        WITH filtered_orders AS (
                                SELECT
                                id,
                                customer_id,
                                delivery_zone_id,
                                CAST((created_at AT TIME ZONE 'UTC' AT TIME ZONE 'GMT-5') AS DATE) AS day,
                                is_billed
                                FROM
                                delivery_orders
                                WHERE
                                is_billed = :isBilled
                                AND status_id IN (:statusIdList)
                                AND created_at BETWEEN :startDate AND :endDate
                        )
                        SELECT
                                do2.customer_id                         AS customerId,
                                COUNT(*)                                AS deliveryOrdersCount,
                                TO_CHAR(do2.day, 'YYYY-MM-DD')          AS day,
                                customers.name                          AS customerName,
                                delivery_zones.name                     AS deliveryZoneName,
                                do2.is_billed                           AS isBilled,
                                STRING_AGG(CAST(do2.id AS TEXT), ',')   AS deliveryOrderIds
                        FROM filtered_orders do2
                        LEFT JOIN customers
                                ON customers.id = do2.customer_id
                        LEFT JOIN delivery_zones
                                ON delivery_zones.id = do2.delivery_zone_id
                        GROUP BY
                                do2.customer_id,
                                do2.day,
                                customers.name,
                                delivery_zones.name,
                                do2.is_billed
                        ORDER BY
                                do2.day DESC
                                """)
        List<CustomerOrdersSummaryDTO> findCustomerOrdersSummary(
                        @Param("isBilled") boolean isBilled,
                        @Param("statusIdList") List<Long> statusIdList,
                        @Param("startDate") Timestamp startDate,
                        @Param("endDate") Timestamp endDate);
}
