package com.tarapaca.api.product_order.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tarapaca.api.generics.infrastructure.repository.GenericProduction.GenericProductionRepository;

@Repository
public interface ProductOrderDataJpaRepository
        extends GenericProductionRepository<ProductOrderData> {
    @Query("SELECT po.isPending FROM ProductOrderData po WHERE po.id = :id")
    boolean getIsPending(Long id);

    @Query("SELECT po.isProduction FROM ProductOrderData po WHERE po.id = :id")
    boolean getIsProduction(Long id);

    @Query("SELECT po.isFinished FROM ProductOrderData po WHERE po.id = :id")
    boolean getIsFinished(Long id);

    @Query("SELECT po.isDelivered FROM ProductOrderData po WHERE po.id = :id")
    boolean getIsDelivered(Long id);

    @Query("SELECT po.isCancelled FROM ProductOrderData po WHERE po.id = :id")
    boolean getIsCancelled(Long id);

    @Query("SELECT po.isPaused FROM ProductOrderData po WHERE po.id = :id")
    boolean getIsPaused(Long id);

    @Query("SELECT po FROM ProductOrderData po WHERE po.deliveryOrderId = :id")
    List<ProductOrderData> findByDeliveryOrderId(Long deliveryOrderId);
}
