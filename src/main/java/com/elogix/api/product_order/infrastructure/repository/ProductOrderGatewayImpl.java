package com.elogix.api.product_order.infrastructure.repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.hibernate.Session;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import com.elogix.api.delivery_order.domain.model.OrderStatusChangeObserver;
import com.elogix.api.delivery_order.infrastructure.repository.delivery_order.DeliveryOrderData;
import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.generics.infrastructure.repository.GenericProduction.GenericProductionGatewayImpl;
import com.elogix.api.product.domain.usecase.ProductUseCase;
import com.elogix.api.product_order.domain.gateway.ProductOrderGateway;
import com.elogix.api.product_order.domain.model.ProductOrder;
import com.elogix.api.product_order.infrastructure.helper.ProductOrderMapper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductOrderGatewayImpl
        extends
        GenericProductionGatewayImpl<ProductOrder, ProductOrderData, ProductOrderDataJpaRepository, ProductOrderMapper>
        implements ProductOrderGateway {
    private final ProductUseCase productUseCase;
    private OrderStatusChangeObserver statusObserver;

    public ProductOrderGatewayImpl(
            ProductOrderDataJpaRepository repository,
            ProductOrderMapper mapper,
            EntityManager entityManager,
            StatusUseCase statusUseCase,
            ProductUseCase productUseCase,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(
                repository,
                mapper,
                entityManager,
                updateUtils,
                statusUseCase,
                deletedFilter);
        this.productUseCase = productUseCase;
        this.statusObserver = null;
    }

    public void addStatusObserver(OrderStatusChangeObserver observer) {
        this.statusObserver = observer;
    }

    @Override
    @Transactional
    public ProductOrder add(ProductOrder productOrder) {
        productOrder.setPending(true);
        Status status = statusUseCase.findByName(EStatus.PENDING, false);
        productOrder.setStatus(status);

        // Ensure DeliveryOrder is managed
        if (productOrder.getDeliveryOrderId() != null) {
            DeliveryOrderData managedDeliveryOrder = updateUtils.getDeliveryOrder(productOrder.getDeliveryOrderId());
            productOrder.setDeliveryOrderId(managedDeliveryOrder.getId());
        }

        ProductOrder productOrderSaved = save(productOrder);

        return productOrderSaved;
    }

    @Override
    @Transactional
    public ProductOrder update(ProductOrder productOrder) {
        // Validate input and get existing order
        if (productOrder == null || productOrder.getId() == null) {
            throw new IllegalArgumentException("Product Order and ID must not be null");
        }

        ProductOrder existingOrder = findById(productOrder.getId(), false);

        // Handle DeliveryOrder association
        handleDeliveryOrderAssociation(productOrder, existingOrder.getDeliveryOrderId());

        // Check if order details have changed
        if (hasOrderDetailsChanged(existingOrder, productOrder)) {
            prepareUpdated(productOrder);
        }

        // Save the updated order
        return save(productOrder);
    }

    private void handleDeliveryOrderAssociation(ProductOrder productOrder, Long existingDeliveryOrderId) {
        // Update delivery order if new ID is provided
        if (productOrder.getDeliveryOrderId() != null
                && !productOrder.getDeliveryOrderId().equals(existingDeliveryOrderId)) {
            DeliveryOrderData deliveryOrder = updateUtils.getDeliveryOrder(productOrder.getDeliveryOrderId());
            productOrder.setDeliveryOrderId(deliveryOrder.getId());
        }
        // Preserve existing delivery order if none provided
        else if (productOrder.getDeliveryOrderId() == null) {
            productOrder.setDeliveryOrderId(existingDeliveryOrderId);
        }
    }

    @Override
    @Transactional
    public ProductOrder deleteById(Long id) {
        ProductOrder order = findById(id, false);
        if (order != null && order.getDeliveryOrderId() != null) {
            DeliveryOrderData deliveryOrder = updateUtils.getDeliveryOrder(order.getDeliveryOrderId());

            if (deliveryOrder.isPending()) {
                hardDelete(id);
                return ProductOrder.builder().id(id).isDeleted(true).build();
            } else {
                return delete(order);
            }
        }
        return order;
    }

    @Override
    @Transactional
    public void deleteByDeliveryOrderId(Long orderId) {
        List<ProductOrder> productOrders = findByDeliveryOrderId(orderId, false);
        if (productOrders != null && !productOrders.isEmpty()) {
            deleteAll(productOrders);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductOrder> findByDeliveryOrderId(Long deliveryOrderId, boolean isDeleted)
            throws IllegalArgumentException, NoSuchElementException {
        List<ProductOrderData> productOrderDataList;
        try (Session session = setDeleteFilter(isDeleted)) {
            productOrderDataList = repository.findByDeliveryOrderId(deliveryOrderId);
            session.disableFilter(this.deletedFilter);
        }

        return productOrderDataList.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void incrementProductHit(ProductOrder productOrder) {
        productUseCase.incrementOneHit(productOrder.getProduct().getId());
    }

    @Override
    public boolean hasOrderDetailsChanged(@NonNull ProductOrder existing, @NonNull ProductOrder updated) {
        if (hasBasicDetailsChanged(existing, updated)) {
            return true;
        }

        if (existing.getProduct().getType().isMeasurable()) {
            return hasMeasurableDetailsChanged(existing, updated);
        }

        return false;
    }

    /**
     * Compares basic details between two ProductOrder objects to determine if there
     * are changes.
     *
     * @param existing The original ProductOrder object being compared
     * @param updated  The modified ProductOrder object to compare against
     * @return true if there are differences in amount, observation or product ID,
     *         false otherwise
     */
    private boolean hasBasicDetailsChanged(ProductOrder existing, ProductOrder updated) {
        return !Objects.equals(existing.getAmount(), updated.getAmount())
                || !Objects.equals(existing.getObservation(), updated.getObservation())
                || !Objects.equals(existing.getProduct().getId(), updated.getProduct().getId());
    }

    private boolean hasMeasurableDetailsChanged(ProductOrder existing, ProductOrder updated) {
        return !Objects.equals(existing.getMeasure1(), updated.getMeasure1())
                || !Objects.equals(existing.getMeasure2(), updated.getMeasure2())
                || !Objects.equals(existing.getMetricUnit().getId(), updated.getMetricUnit().getId())
                || !Objects.equals(existing.getMeasureDetail().getId(), updated.getMeasureDetail().getId());
    }

    @Override
    public ProductOrder advanceOrderStatus(Long id) {
        ProductOrder order = super.advanceOrderStatus(id);
        notifyStatusChanges(order);
        return order;
    }

    // TODO: Implement this method in the future:
    // advanceOrderStatus(List<Long> ids)

    private void notifyStatusChanges(ProductOrder order) {
        if (order == null || order.getDeliveryOrderId() == null) {
            return;
        }

        Long deliveryOrderId = order.getDeliveryOrderId();
        EStatus status = order.getStatus().getName();

        try {
            statusObserver.onStatusChanged(deliveryOrderId, status);
            log.info("Status change notification sent for delivery order {}", deliveryOrderId);
        } catch (Exception e) {
            log.error("Failed to notify status change for delivery order {}: {}", deliveryOrderId, e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductOrder toggleIsCancelled(Long id) {
        // Validar parámetros
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid product order ID");
        }

        // Obtener las entidades necesarias
        ProductOrder productOrder = findById(id, false);
        DeliveryOrderData deliveryOrder = updateUtils.getDeliveryOrder(productOrder.getDeliveryOrderId());

        // Extraer la lógica de estado a métodos descriptivos
        boolean canBeCancelled = isOrderCancellable(productOrder);
        boolean canBeRestored = isOrderRestorable(productOrder);
        boolean canBeDeleted = isOrderDeletable(productOrder, deliveryOrder);

        try {
            // Aplicar la transición de estado apropiada
            if (canBeDeleted) {
                hardDelete(id);
                return productOrder;
            }

            if (canBeRestored) {
                setUpdateStatus(productOrder, EStatus.PRODUCTION);
            } else if (canBeCancelled) {
                setUpdateStatus(productOrder, EStatus.CANCELLED);
            } else {
                // No se puede cambiar el estado
                return productOrder;
            }

            return save(productOrder);

        } catch (Exception e) {
            log.error("Error toggling cancelled state for product order {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to toggle cancelled state", e);
        }
    }

    /**
     * Verifica si la orden puede ser cancelada
     */
    private boolean isOrderCancellable(ProductOrder order) {
        return (order.isPending() || order.isProduction()) && !order.isCancelled();
    }

    /**
     * Verifica si la orden puede ser restaurada
     */
    private boolean isOrderRestorable(ProductOrder order) {
        return order.isCancelled();
    }

    /**
     * Verifica si la orden puede ser eliminada
     */
    private boolean isOrderDeletable(ProductOrder order, DeliveryOrderData deliveryOrder) {
        return (order.isPending() || order.isProduction())
                && deliveryOrder.isPending();
    }
}
