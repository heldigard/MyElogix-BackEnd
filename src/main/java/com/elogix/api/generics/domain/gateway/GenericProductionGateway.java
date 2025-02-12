package com.elogix.api.generics.domain.gateway;

import java.util.List;

import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.generics.domain.model.GenericProduction;

/**
 * Gateway interface for production-related entities with status management
 *
 * @param <T> Type that extends GenericProductionEntity
 */
public interface GenericProductionGateway<T extends GenericProduction>
        extends GenericStatusGateway<T> {
    // Status Transitions
    T advanceOrderStatus(Long id);

    List<T> advanceOrderStatus(List<Long> idList);

    void validateStatusChangeTimeElapsed(T order, EStatus currentStatus, EStatus newStatus);

    List<T> processStatusChange(List<T> orders);

    void setUpdateStatus(T order, EStatus statusName);

    void validateStatusTransition(EStatus current, EStatus next);

    Status findNextStatus(Status status);

    EStatus findNextStatus(EStatus status);

    // Status Getters
    boolean getIsPending(Long id);

    boolean getIsProduction(Long id);

    boolean getIsFinished(Long id);

    boolean getIsDelivered(Long id);

    boolean getIsCancelled(Long id);

    boolean getIsPaused(Long id);

    T toggleIsCancelled(Long id);

    T toggleIsPaused(Long id);

    boolean shouldBeFinished(List<? extends GenericProduction> orders);

    boolean shouldBeDelivered(List<? extends GenericProduction> orders);
}
