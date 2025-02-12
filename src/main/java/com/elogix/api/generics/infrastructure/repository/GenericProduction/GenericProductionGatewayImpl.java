package com.elogix.api.generics.infrastructure.repository.GenericProduction;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.elogix.api.delivery_order.domain.model.DeliveryOrder;
import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.generics.domain.gateway.GenericProductionGateway;
import com.elogix.api.generics.domain.model.GenericProduction;
import com.elogix.api.generics.domain.model.StatusFields;
import com.elogix.api.generics.infrastructure.helpers.GenericProductionMapper;
import com.elogix.api.generics.infrastructure.repository.GenericStatus.GenericStatusGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.users.domain.model.UserBasic;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Generic implementation for production-related gateway operations.
 *
 * @param <T> The domain entity type extending GenericProduction
 * @param <D> The data entity type extending GenericProductionData
 * @param <R> The repository type extending both JpaRepository and
 *            GenericProductionRepository
 * @param <M> The mapper type extending GenericMapperGateway
 */
@Slf4j
public abstract class GenericProductionGatewayImpl<T extends GenericProduction, D extends GenericProductionData, R extends JpaRepository<D, Long> & GenericProductionRepository<D>, M extends GenericProductionMapper<T, D>>
        extends GenericStatusGatewayImpl<T, D, R, M>
        implements GenericProductionGateway<T> {

    /**
     * Constructor for GenericProductionGatewayImpl
     */
    protected GenericProductionGatewayImpl(
            R repository,
            M mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            StatusUseCase statusUseCase,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, statusUseCase, deletedFilter);
    }

    private static final long MINIMUM_DELAY_SECONDS = 10;

    @Override
    public void preserveExistingData(T order, T existing) {
        super.preserveExistingData(order, existing);

        preserveProductionFields(order, existing);
        preserveFinishedFields(order, existing);
        preserveDeliveredFields(order, existing);
        preserveCancelledFields(order, existing);
        preservePausedFields(order, existing);
        preserveStatus(order, existing);
        preserveStatusFlags(order, existing);
    }

    private void preserveProductionFields(T order, T existing) {
        if (order.getProductionAt() == null)
            order.setProductionAt(existing.getProductionAt());
        if (order.getProductionBy() == null)
            order.setProductionBy(existing.getProductionBy());
    }

    private void preserveFinishedFields(T order, T existing) {
        if (order.getFinishedAt() == null)
            order.setFinishedAt(existing.getFinishedAt());
        if (order.getFinishedBy() == null)
            order.setFinishedBy(existing.getFinishedBy());
    }

    private void preserveDeliveredFields(T order, T existing) {
        if (order.getDeliveredAt() == null)
            order.setDeliveredAt(existing.getDeliveredAt());
        if (order.getDeliveredBy() == null)
            order.setDeliveredBy(existing.getDeliveredBy());
    }

    private void preserveCancelledFields(T order, T existing) {
        if (order.getCancelledAt() == null)
            order.setCancelledAt(existing.getCancelledAt());
        if (order.getCancelledBy() == null)
            order.setCancelledBy(existing.getCancelledBy());
    }

    private void preservePausedFields(T order, T existing) {
        if (order.getPausedAt() == null)
            order.setPausedAt(existing.getPausedAt());
        if (order.getPausedBy() == null)
            order.setPausedBy(existing.getPausedBy());
    }

    private void preserveStatus(T order, T existing) {
        if (order.getStatus() == null)
            order.setStatus(existing.getStatus());
    }

    private void preserveStatusFlags(T order, T existing) {
        if (!order.isPending())
            order.setPending(existing.isPending());
        if (!order.isProduction())
            order.setProduction(existing.isProduction());
        if (!order.isFinished())
            order.setFinished(existing.isFinished());
        if (!order.isDelivered())
            order.setDelivered(existing.isDelivered());
        if (!order.isCancelled())
            order.setCancelled(existing.isCancelled());
        if (!order.isPaused())
            order.setPaused(existing.isPaused());
    }

    @Override
    public boolean getIsPending(Long id) {
        return repository.getIsPending(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean getIsProduction(Long id) {
        return repository.getIsProduction(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean getIsFinished(Long id) {
        return repository.getIsFinished(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean getIsDelivered(Long id) {
        return repository.getIsDelivered(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean getIsCancelled(Long id) {
        return repository.getIsCancelled(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean getIsPaused(Long id) {
        return repository.getIsPaused(id);
    }

    @Override
    @Transactional
    public T toggleIsCancelled(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        T order = findById(id, false);
        boolean isPending = order.isPending() || order.isProduction();
        boolean isCancelled = order.isCancelled();

        try {
            if (isCancelled) {
                setUpdateStatus(order, EStatus.PRODUCTION);
            } else if (isPending) {
                setUpdateStatus(order, EStatus.CANCELLED);
            } else {
                return order;
            }
        } catch (Exception e) {
            return order;
        }

        return save(order);
    }

    @Override
    @Transactional
    public T toggleIsPaused(Long id) {
        T order = findById(id, false);
        boolean isPending = order.isPending() || order.isProduction();
        boolean isPaused = order.isPaused();

        try {
            if (isPaused) {
                setUpdateStatus(order, EStatus.PRODUCTION);
            } else if (isPending) {
                setUpdateStatus(order, EStatus.PAUSED);
            } else {
                return order;
            }
        } catch (Exception e) {
            return order;
        }

        return save(order);
    }

    @Override
    public Status findNextStatus(Status status) {
        EStatus newName = switch (status.getName()) {
            case PENDING -> EStatus.PRODUCTION;
            case PRODUCTION -> EStatus.FINISHED;
            case FINISHED -> EStatus.DELIVERED;
            default -> status.getName();
        };

        return statusUseCase.findByName(newName, false);
    }

    @Override
    public EStatus findNextStatus(EStatus status) {
        return switch (status) {
            case PENDING -> EStatus.PRODUCTION;
            case PRODUCTION -> EStatus.FINISHED;
            case FINISHED -> EStatus.DELIVERED;
            default -> status;
        };
    }

    @Override
    @Transactional
    public T advanceOrderStatus(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        try {
            T order = findById(id, false);
            return processStatusChange(Collections.singletonList(order)).getFirst();
        } catch (Exception e) {
            log.error("Error processing status change for order {}: {}", id, e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public List<T> advanceOrderStatus(List<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            throw new IllegalArgumentException("Order ID list cannot be null or empty");
        }

        try {
            List<T> orderList = findByIdIn(idList, null, false);
            return processStatusChange(orderList);
        } catch (Exception e) {
            log.error("Error processing status change for orders {}: {}", idList, e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public List<T> processStatusChange(List<T> orders) {
        boolean anyUpdated = false;

        try {
            for (T order : orders) {
                boolean updated = handleNextStatusChange(order);
                anyUpdated = anyUpdated || updated;
            }

            if (!anyUpdated) {
                return orders;
            }

            List<T> savedOrders = saveAll(orders);

            return savedOrders;

        } catch (Exception e) {
            log.error("Status change timing issue: {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    private boolean handleNextStatusChange(T order) {
        EStatus currentStatus = order.getStatus().getName();
        EStatus newStatus = findNextStatus(currentStatus);

        if (currentStatus == newStatus) {
            return false;
        }
        if (newStatus == EStatus.FINISHED && order instanceof DeliveryOrder) {
            DeliveryOrder deliveryOrder = (DeliveryOrder) order;
            boolean shouldBeFinished = shouldBeFinished(deliveryOrder.getProductOrders());
            if (!shouldBeFinished) {
                return false;
            }
        }
        if (newStatus == EStatus.DELIVERED && order instanceof DeliveryOrder) {
            DeliveryOrder deliveryOrder = (DeliveryOrder) order;
            boolean shouldBeFinished = shouldBeDelivered(deliveryOrder.getProductOrders());
            if (!shouldBeFinished) {
                return false;
            }
        }

        try {
            setUpdateStatus(order, newStatus);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean shouldBeFinished(List<? extends GenericProduction> orders) {
        if (orders == null || orders.isEmpty()) {
            return false;
        }
        return orders.stream()
                .allMatch(order -> order.isFinished() || order.isCancelled());
    }

    @Override
    public boolean shouldBeDelivered(List<? extends GenericProduction> orders) {
        if (orders == null || orders.isEmpty()) {
            return false;
        }
        return orders.stream()
                .allMatch(order -> order.isDelivered() || order.isCancelled());
    }

    @Override
    public void validateStatusTransition(EStatus current, EStatus next) {
        boolean validTransition;
        switch (current) {
            case PENDING:
                validTransition = Set.of(EStatus.PENDING, EStatus.PRODUCTION, EStatus.CANCELLED, EStatus.PAUSED)
                        .contains(next);
                break;
            case PRODUCTION:
                validTransition = Set.of(EStatus.PRODUCTION, EStatus.FINISHED, EStatus.CANCELLED, EStatus.PAUSED)
                        .contains(next);
                break;
            case FINISHED:
                validTransition = Set.of(EStatus.FINISHED, EStatus.DELIVERED).contains(next);
                break;
            case DELIVERED:
                validTransition = Set.of(EStatus.FINISHED, EStatus.FINISHED).contains(next);
                break;
            case CANCELLED:
                validTransition = Set.of(EStatus.CANCELLED, EStatus.PENDING, EStatus.PRODUCTION, EStatus.PAUSED)
                        .contains(next);
                break;
            case PAUSED:
                validTransition = Set.of(EStatus.PAUSED, EStatus.PENDING, EStatus.PRODUCTION, EStatus.CANCELLED)
                        .contains(next);
                break;
            default:
                validTransition = false;
                break;
        }

        if (!validTransition) {
            throw new IllegalStateException(
                    String.format("Cannot transition order status from %s to %s", current, next));
        }
    }

    @Override
    public void validateStatusChangeTimeElapsed(T order, EStatus currentStatus, EStatus newStatus) {
        Instant lastStatusChange = getLastStatusChangeTime(order, currentStatus);
        // Use the specific timezone to align with Instant fields
        Instant now = Instant.now();
        long secondsElapsed = ChronoUnit.SECONDS.between(lastStatusChange, now);

        if (secondsElapsed < MINIMUM_DELAY_SECONDS) {
            handleStatusChangeTimingViolation(currentStatus, newStatus, MINIMUM_DELAY_SECONDS - secondsElapsed);
        }
    }

    private Instant getLastStatusChangeTime(T order, EStatus currentStatus) {
        return switch (currentStatus) {
            case PENDING -> order.getCreatedAt();
            case PRODUCTION -> order.getProductionAt();
            case FINISHED -> order.getFinishedAt();
            case DELIVERED -> order.getDeliveredAt();
            case CANCELLED -> order.getCancelledAt();
            case PAUSED -> order.getPausedAt();
            default -> Instant.now();
        };
    }

    private void handleStatusChangeTimingViolation(EStatus currentStatus, EStatus newStatus, long remainingSeconds) {
        log.error(
                String.format(
                        "Cannot change status from %s to %s. Please wait %d seconds before changing status again.",
                        currentStatus, newStatus, remainingSeconds),
                remainingSeconds,
                currentStatus,
                newStatus);
    }

    /**
     * Updates the status of the given data object and sets the appropriate status
     * flags and timestamps.
     *
     * @param order     The domain object whose status is to be updated.
     * @param newStatus The new status to be set.
     * @return The updated data object.
     * @throws IllegalStateException If no authenticated user is found.
     */
    @Override
    public void setUpdateStatus(T order, EStatus newStatus) {
        UserBasic user = updateUtils.getCurrentUser();
        EStatus currentStatus = order.getStatus().getName();

        // Skip if no status change
        if (newStatus == currentStatus) {
            return;
        }

        validateStatusChangeTimeElapsed(order, currentStatus, newStatus);
        validateStatusTransition(currentStatus, newStatus);

        Status status = statusUseCase.findByName(newStatus, false);
        order.setStatus(status);

        StatusFields fields = StatusFields.forStatus(newStatus);
        Instant now = Instant.now();

        // Set status flags
        order.setPending(fields.pending());
        order.setProduction(fields.production());
        order.setFinished(fields.finished());
        order.setDelivered(fields.delivered());
        order.setCancelled(fields.cancelled());
        order.setPaused(fields.paused());

        // Handle status specific updates: userBy and dateAt
        switch (newStatus) {
            case PENDING -> {
                // Reset all fields for PENDING
                resetAllStatusFields(order);
                order.setUpdatedBy(user);
            }
            case PRODUCTION -> {
                order.setProductionAt(now);
                order.setProductionBy(user);
                if (order.isPaused()) {
                    order.setPausedAt(null);
                    order.setPausedBy(null);
                }
                if (order.isCancelled()) {
                    order.setCancelledAt(null);
                    order.setCancelledBy(null);
                }
            }
            case FINISHED -> {
                order.setFinishedAt(now);
                order.setFinishedBy(user);
                if (order.isPaused()) {
                    order.setPausedAt(null);
                    order.setPausedBy(null);
                }
            }
            case DELIVERED -> {
                order.setDeliveredAt(now);
                order.setDeliveredBy(user);
                if (order.isPaused()) {
                    order.setPausedAt(null);
                    order.setPausedBy(null);
                }
            }
            case CANCELLED -> {
                order.setCancelledAt(now);
                order.setCancelledBy(user);
                if (order.isPaused()) {
                    order.setPausedAt(null);
                    order.setPausedBy(null);
                }
            }
            case PAUSED -> {
                order.setPausedAt(now);
                order.setPausedBy(user);
            }

            default -> {// Do nothing
            }
        }
    }

    private void resetAllStatusFields(T order) {
        order.setProductionAt(null);
        order.setFinishedAt(null);
        order.setDeliveredAt(null);
        order.setCancelledAt(null);
        order.setPausedAt(null);
        order.setProductionBy(null);
        order.setFinishedBy(null);
        order.setDeliveredBy(null);
        order.setCancelledBy(null);
        order.setPausedBy(null);
    }
}
