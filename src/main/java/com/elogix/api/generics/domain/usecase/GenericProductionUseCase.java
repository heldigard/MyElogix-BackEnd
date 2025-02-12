package com.elogix.api.generics.domain.usecase;

import java.util.List;

import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.generics.domain.gateway.GenericProductionGateway;
import com.elogix.api.generics.domain.model.GenericProduction;

/**
 * Abstract base class for production-related use cases that handles generic
 * production entities.
 *
 * @param <T> The domain model type that extends GenericProduction, representing
 *            the specific
 *            production entity being managed
 * @param <G> The gateway type that extends GenericProductionGateway<T>,
 *            providing data access
 *            and persistence operations for the production entity
 */
public abstract class GenericProductionUseCase<T extends GenericProduction, G extends GenericProductionGateway<T>>
        extends GenericStatusUseCase<T, G> {

    protected GenericProductionUseCase(G gateway) {
        super(gateway);
    }

    public T advanceOrderStatus(Long id) {
        return gateway.advanceOrderStatus(id);
    }

    public List<T> advanceOrderStatus(List<Long> idList) {
        return gateway.advanceOrderStatus(idList);
    }

    public void validateStatusChangeTimeElapsed(T order, EStatus currentStatus, EStatus newStatus) {
        gateway.validateStatusChangeTimeElapsed(order, currentStatus, newStatus);
    }

    public List<T> processStatusChange(List<T> orders) {
        return gateway.processStatusChange(orders);
    }

    public EStatus findNextStatus(EStatus status) {
        return gateway.findNextStatus(status);
    }

    public Status findNextStatus(Status status) {
        return gateway.findNextStatus(status);
    }

    public void setUpdateStatus(T order, EStatus statusName) {
        gateway.setUpdateStatus(order, statusName);
    }

    public Boolean getIsFinished(Long id) {
        return gateway.getIsFinished(id);
    }

    public Boolean getIsDelivered(Long id) {
        return gateway.getIsDelivered(id);
    }

    public Boolean getIsCancelled(Long id) {
        return gateway.getIsCancelled(id);
    }

    public Boolean getIsPaused(Long id) {
        return gateway.getIsPaused(id);
    }

    public T toggleIsCancelled(Long id) {
        return gateway.toggleIsCancelled(id);
    }

    public T toggleIsPaused(Long id) {
        return gateway.toggleIsPaused(id);
    }

    boolean shouldBeFinished(List<? extends GenericProduction> orders) {
        return gateway.shouldBeFinished(orders);
    }

    boolean shouldBeDelivered(List<? extends GenericProduction> orders) {
        return gateway.shouldBeDelivered(orders);
    }
}
