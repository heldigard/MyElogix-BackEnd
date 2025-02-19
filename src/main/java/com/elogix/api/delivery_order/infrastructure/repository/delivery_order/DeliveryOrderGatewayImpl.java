package com.elogix.api.delivery_order.infrastructure.repository.delivery_order;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.elogix.api.customers.domain.model.BranchOffice;
import com.elogix.api.customers.domain.model.Customer;
import com.elogix.api.customers.domain.model.DeliveryZoneBasic;
import com.elogix.api.customers.domain.usecase.BranchOfficeUseCase;
import com.elogix.api.customers.domain.usecase.CustomerUseCase;
import com.elogix.api.customers.domain.usecase.DeliveryZoneBasicUseCase;
import com.elogix.api.delivery_order.domain.gateway.DeliveryOrderGateway;
import com.elogix.api.delivery_order.domain.model.DeliveryOrder;
import com.elogix.api.delivery_order.domain.model.OrderStatusChangeObserver;
import com.elogix.api.delivery_order.dto.DeliveryOrderResponse;
import com.elogix.api.delivery_order.infrastructure.helper.mapper.DeliveryOrderMapper;
import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.MeasureDetail;
import com.elogix.api.delivery_orders.domain.model.MetricUnit;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.delivery_orders.domain.usecase.MeasureDetailUseCase;
import com.elogix.api.delivery_orders.domain.usecase.MetricUnitUseCase;
import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.generics.infrastructure.repository.GenericProduction.GenericProductionGatewayImpl;
import com.elogix.api.product.domain.model.ProductBasic;
import com.elogix.api.product.domain.usecase.ProductBasicUseCase;
import com.elogix.api.product.domain.usecase.ProductUseCase;
import com.elogix.api.product_order.domain.model.ProductOrder;
import com.elogix.api.product_order.domain.usecase.ProductOrderUseCase;
import com.elogix.api.shared.domain.model.pagination.DateRange;
import com.elogix.api.shared.domain.model.pagination.PaginationCriteria;
import com.elogix.api.shared.infraestructure.helpers.RequestUtils;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.users.domain.model.UserBasic;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the DeliveryOrderGateway interface that handles delivery
 * order operations.
 * This class extends GenericProductionGatewayImpl and manages the persistence
 * and business logic
 * for delivery orders, including their product orders, customer relationships,
 * and status changes.
 * <p>
 * Key responsibilities include:
 * - Managing delivery order creation, updates and deletions
 * - Handling associations between delivery orders and related entities
 * - Managing order status transitions and state
 * - Tracking billing status
 * - Managing product order relationships
 * - Maintaining audit information
 * <p>
 * The implementation provides transactional support and handles bidirectional
 * relationships
 * between entities. It also includes functionality for:
 * - Customer statistics tracking
 * - Order status progression
 * - Billing status management
 * - Product order handling
 * - Delivery zone and branch office associations
 *
 * @see DeliveryOrderGateway
 * @see GenericProductionGatewayImpl
 * @see DeliveryOrder
 * @see DeliveryOrderData
 * @see ProductOrder
 * @see CustomerUseCase
 * @see StatusUseCase
 * @see ProductUseCase
 */
@Slf4j
public class DeliveryOrderGatewayImpl extends
        GenericProductionGatewayImpl<DeliveryOrder, DeliveryOrderData, DeliveryOrderDataJpaRepository, DeliveryOrderMapper>
        implements DeliveryOrderGateway, OrderStatusChangeObserver {
    private ProductOrderUseCase productOrderUseCase;
    private final CustomerUseCase customerUseCase;
    private final DeliveryZoneBasicUseCase deliveryZoneUseCase;
    private final BranchOfficeUseCase officeUseCase;
    private final MetricUnitUseCase metricUnitUseCase;
    private final MeasureDetailUseCase measureDetailUseCase;
    private final ProductBasicUseCase productBasicUseCase;
    private final ProductUseCase productUseCase;
    private final RequestUtils requestUtils;

    public DeliveryOrderGatewayImpl(
            DeliveryOrderDataJpaRepository repository,
            DeliveryOrderMapper mapper,
            CustomerUseCase customerUseCase,
            StatusUseCase statusUseCase,
            ProductUseCase productUseCase,
            EntityManager entityManager,
            DeliveryZoneBasicUseCase deliveryZoneUseCase,
            BranchOfficeUseCase officeUseCase,
            MetricUnitUseCase metricUnitUseCase,
            MeasureDetailUseCase measureDetailUseCase,
            ProductBasicUseCase productBasicUseCase,
            UpdateUtils updateUtils,
            RequestUtils requestUtils,
            String deletedFilter) {
        super(
                repository,
                mapper,
                entityManager,
                updateUtils,
                statusUseCase,
                deletedFilter);
        this.customerUseCase = customerUseCase;
        this.productUseCase = productUseCase;
        this.deliveryZoneUseCase = deliveryZoneUseCase;
        this.officeUseCase = officeUseCase;
        this.metricUnitUseCase = metricUnitUseCase;
        this.measureDetailUseCase = measureDetailUseCase;
        this.productBasicUseCase = productBasicUseCase;
        this.productOrderUseCase = null;
        this.requestUtils = requestUtils;
    }

    @Autowired
    public void setProductOrderUseCase(ProductOrderUseCase productOrderUseCase) {
        this.productOrderUseCase = productOrderUseCase;
    }

    @Override
    public DeliveryOrder nuevo(Long customerId) {
        // Get customer
        Customer customer = customerUseCase.findById(customerId, false);

        // Safely get first branch office or throw meaningful exception
        BranchOffice branchOffice = customer.getBranchOfficeList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Customer with ID " + customerId + " has no associated branch offices"));

        // Get delivery zone with fallback to neighborhood's zone
        DeliveryZoneBasic deliveryZone = branchOffice.getDeliveryZone();
        if (deliveryZone == null && branchOffice.getNeighborhood() != null) {
            deliveryZone = branchOffice.getNeighborhood().getDeliveryZone();
        }

        // Build and return new delivery order
        return DeliveryOrder.builder()
                .createdAt(Instant.now())
                .customer(customer)
                .branchOffice(branchOffice)
                .deliveryZone(deliveryZone)
                .productOrders(List.of())
                .build();
    }

    private DeliveryOrder manageAssociations(DeliveryOrder deliveryOrder) {
        // Ensure Customer association
        if (deliveryOrder.getCustomer() != null) {
            Customer managedCustomer = customerUseCase.findById(deliveryOrder.getCustomer().getId(), false);
            deliveryOrder.setCustomer(managedCustomer);
        }

        // Ensure DeliveryZone association
        if (deliveryOrder.getDeliveryZone() != null) {
            DeliveryZoneBasic managedDeliveryZone = deliveryZoneUseCase
                    .findById(deliveryOrder.getDeliveryZone().getId(), false);
            deliveryOrder.setDeliveryZone(managedDeliveryZone);
        }

        // Ensure BranchOffice association
        if (deliveryOrder.getBranchOffice() != null) {
            BranchOffice managedBranchOffice = officeUseCase
                    .findById(deliveryOrder.getBranchOffice().getId(), false);
            deliveryOrder.setBranchOffice(managedBranchOffice);
        }

        // Ensure Status association
        if (deliveryOrder.getStatus() != null) {
            Status managedStatus = statusUseCase.findById(deliveryOrder.getStatus().getId(), false);
            deliveryOrder.setStatus(managedStatus);
        }

        // Manage ProductOrderData associations
        List<ProductOrder> productOrders = deliveryOrder.getProductOrders();
        if (productOrders != null && !productOrders.isEmpty()) {
            productOrders.forEach(this::manageProductOrderAssociations);
        }

        return deliveryOrder;
    }

    private void manageProductOrderAssociations(ProductOrder productOrder) {
        // Ensure Product association
        ProductBasic managedProduct = productBasicUseCase.findById(productOrder.getProduct().getId(), false);
        productOrder.setProduct(managedProduct);

        // Ensure MetricUnit association if present
        if (productOrder.getMetricUnit() != null) {
            MetricUnit managedMetricUnit = metricUnitUseCase.findById(productOrder.getMetricUnit().getId(), false);
            productOrder.setMetricUnit(managedMetricUnit);
        }

        // Ensure MeasureDetail association if present
        if (productOrder.getMeasureDetail() != null) {
            MeasureDetail managedMeasureDetail = measureDetailUseCase
                    .findById(productOrder.getMeasureDetail().getId(), false);
            productOrder.setMeasureDetail(managedMeasureDetail);
        }

        // Ensure Status association
        Status managedStatus = statusUseCase.findById(productOrder.getStatus().getId(), false);
        productOrder.setStatus(managedStatus);
    }

    private void updateBidirectionalRelationships(DeliveryOrder deliveryOrder) {
        List<ProductOrder> productOrders = deliveryOrder.getProductOrders();
        if (productOrders == null || productOrders.isEmpty()) {
            return;
        }

        // Filter only ProductOrders that need updating
        List<ProductOrder> productOrdersToUpdate = productOrders.stream()
                .filter(productOrder -> productOrder.getDeliveryOrderId() == null ||
                        !productOrder.getDeliveryOrderId().equals(deliveryOrder.getId()))
                .toList();

        if (!productOrdersToUpdate.isEmpty()) {
            // Update deliveryOrderId only for filtered items
            productOrdersToUpdate.forEach(productOrder -> productOrder.setDeliveryOrderId(deliveryOrder.getId()));
            // Save only the updated ProductOrders
            productOrderUseCase.saveAll(productOrdersToUpdate);
        }
    }

    private void initializeOrderStatus(DeliveryOrder order) {
        UserBasic currentUser = updateUtils.getCurrentUser();
        Status pendingStatus = getPendingStatus();

        // Initialize main order status
        order.setPending(true);
        order.setProduction(false);
        order.setFinished(false);
        order.setDelivered(false);
        order.setCancelled(false);
        order.setStatus(pendingStatus);
        order.setCreatedBy(currentUser);

        // Initialize product orders status
        if (order.getProductOrders() != null) {
            order.getProductOrders().forEach(po -> initializeNewProductOrder(po, currentUser, pendingStatus));
        }
    }

    @Override
    @Transactional
    public DeliveryOrder add(DeliveryOrder order) {
        log.debug("Adding new DeliveryOrder: {}", order);
        // Then manage other associations
        manageAssociations(order);

        // Initialize the rest of the order status
        initializeOrderStatus(order);

        // Save delivery order
        DeliveryOrder saved = saveDeliveryOrder(order);

        // Update statistics
        updateStatistics(saved);

        return saved;
    }

    @Override
    @Transactional
    public DeliveryOrder update(DeliveryOrder order) {
        UserBasic currentUser = updateUtils.getCurrentUser();
        DeliveryOrder existing = findById(order.getId(), false);

        if (order.getStatus() != null) {
            validateStatusTransition(existing.getStatus().getName(), order.getStatus().getName());
        }

        preserveExistingData(order, existing);
        prepareUpdated(order);
        handleProductOrders(order, existing, currentUser);

        DeliveryOrder saved = saveDeliveryOrder(order);
        updateStatistics(saved);

        return saved;
    }

    private Status getPendingStatus() {
        return statusUseCase.findByName(EStatus.PENDING, false);
    }

    @Override
    public void preserveExistingData(DeliveryOrder order, DeliveryOrder existing) {
        super.preserveExistingData(order, existing);

        if (!order.isBilled())
            order.setBilled(existing.isBilled());
        if (order.getBilledAt() == null)
            order.setBilledAt(existing.getBilledAt());
        if (order.getBilledBy() == null)
            order.setBilledBy(existing.getBilledBy());
        if (order.getTotalPrice() == null)
            order.setTotalPrice(existing.getTotalPrice());
        // Preserve associations
        if (order.getCustomer() == null)
            order.setCustomer(existing.getCustomer());
        if (order.getBranchOffice() == null)
            order.setBranchOffice(existing.getBranchOffice());
        if (order.getDeliveryZone() == null)
            order.setDeliveryZone(existing.getDeliveryZone());
        if (order.getProductOrders() == null)
            order.setProductOrders(existing.getProductOrders());
    }

    private void handleProductOrders(DeliveryOrder order, DeliveryOrder existing,
            UserBasic currentUser) {
        if (order.getProductOrders() == null || order.getProductOrders().isEmpty()) {
            return;
        }

        Map<Long, ProductOrder> existingList = getExistingProductOrders(existing);

        order.getProductOrders()
                .forEach(po -> updateProductOrder(po, existingList, currentUser, order.getStatus()));
    }

    private Map<Long, ProductOrder> getExistingProductOrders(DeliveryOrder existing) {
        return existing.getProductOrders() != null
                ? existing.getProductOrders().stream()
                        .collect(Collectors.toMap(po -> po.getId(), po -> po))
                : new HashMap<Long, ProductOrder>();
    }

    private void updateProductOrder(ProductOrder po, Map<Long, ProductOrder> existingList,
            UserBasic currentUser, Status existingStatus) {
        Long poId = po.getId();
        if (poId != null && poId != -1 && existingList.containsKey(poId)) {
            ProductOrder existingPo = existingList.get(poId);
            if (productOrderUseCase.hasOrderDetailsChanged(existingPo, po)
                    && !existingPo.isFinished() && !existingPo.isDelivered()) {
                productOrderUseCase.preserveExistingData(po, existingPo);
                po.setUpdatedBy(currentUser);
                po.setUpdatedAt(Instant.now());
            }
        } else {
            initializeNewProductOrder(po, currentUser, existingStatus);
        }
    }

    private ProductOrder initializeNewProductOrder(ProductOrder po, UserBasic currentUser, Status status) {
        if (po == null || currentUser == null || status == null) {
            throw new IllegalArgumentException("ProductOrder, UserBasic and Status cannot be null");
        }

        // Set all status flags using enum equals()
        EStatus statusName = status.getName();
        po.setPending(EStatus.PENDING.equals(statusName));
        po.setProduction(EStatus.PRODUCTION.equals(statusName));
        po.setFinished(EStatus.FINISHED.equals(statusName));
        po.setDelivered(EStatus.DELIVERED.equals(statusName));
        po.setCancelled(EStatus.CANCELLED.equals(statusName));
        po.setPaused(EStatus.PAUSED.equals(statusName));

        po.setStatus(status);
        po.setCreatedBy(currentUser);
        po.setCreatedAt(Instant.now());
        if (statusName == EStatus.PRODUCTION) {
            po.setProductionAt(Instant.now());
            po.setProductionBy(currentUser);
        }

        return po;
    }

    private DeliveryOrder saveDeliveryOrder(DeliveryOrder order) {
        manageAssociations(order);
        DeliveryOrder saved = save(order);
        updateBidirectionalRelationships(saved);
        return saved;
    }

    private void updateStatistics(DeliveryOrder newOrder) {
        this.incrementCustomerHit(newOrder.getCustomer().getId());
        this.incrementProductOrdersHit(newOrder.getProductOrders());
    }

    @Override
    public DeliveryOrder updateIsBilled(Long id, boolean isBilled) {
        DeliveryOrder order = findById(id, false);
        order.setBilled(isBilled);
        order.setBilledAt(isBilled ? Instant.now() : null);
        order.setBilledBy(isBilled ? updateUtils.getCurrentUser() : null);

        return save(order);
    }

    @Override
    public List<DeliveryOrder> updateIsBilled(List<Long> ids, boolean isBilled) {
        List<DeliveryOrder> orders = findByIdIn(ids, new ArrayList<>(), false);
        orders.forEach(order -> {
            order.setBilled(isBilled);
            order.setBilledAt(isBilled ? Instant.now() : null);
            order.setBilledBy(isBilled ? updateUtils.getCurrentUser() : null);
        });

        return saveAll(orders);
    }

    @Override
    public void incrementCustomerHit(Long customerId) {
        this.customerUseCase.incrementOneHit(customerId);
    }

    @Override
    public void incrementProductOrdersHit(List<ProductOrder> productOrders) {
        // TODO hacer que todos los productOrder hits se hagan masivamente
        productOrders.forEach(productOrder -> productUseCase.incrementOneHit(productOrder.getProduct().getId()));
    }

    @Override
    @Transactional
    public boolean isOrderFinished(Long id) {
        DeliveryOrder order = findById(id, false);
        // If not marked as finished, verify if it should be
        if (!order.isFinished()) {
            return checkAndUpdateOrderFinishedStatus(order);
        }

        return false;
    }

    private boolean checkAndUpdateOrderFinishedStatus(DeliveryOrder order) {
        // Get all product orders
        List<ProductOrder> productOrders = order.getProductOrders();
        boolean shouldBeFinished = areAllProductOrdersFinishedOrCancelled(productOrders);

        // Only update if status should change
        if (shouldBeFinished && !order.isFinished()) {
            EStatus currentStatus = order.getStatus().getName();
            EStatus newStatus = findNextStatus(currentStatus);

            if (currentStatus == newStatus) {
                return false;
            }

            // Update order status
            setUpdateStatus(order, newStatus);
            DeliveryOrder saved = save(order);
            return saved.isFinished();
        }

        return shouldBeFinished;
    }

    private boolean areAllProductOrdersFinishedOrCancelled(List<ProductOrder> productOrders) {
        if (productOrders == null || productOrders.isEmpty()) {
            return false;
        }

        // Check if all product orders are finished or cancelled
        return productOrders.stream()
                .allMatch(productOrder -> productOrder.isFinished() || productOrder.isCancelled());
    }

    @Override
    @Transactional
    public boolean isOrderDelivered(Long id) {
        DeliveryOrder order = findById(id, false);
        // If not marked as finished, verify if it should be
        if (!order.isDelivered()) {
            return checkAndUpdateOrderDeliveredStatus(order);
        }

        return order.isDelivered();
    }

    private boolean checkAndUpdateOrderDeliveredStatus(DeliveryOrder order) {
        // Get all product orders
        List<ProductOrder> productOrders = order.getProductOrders();
        boolean shouldBeDelivered = areAllProductOrdersDeliveredOrCancelled(productOrders);

        // Only update if status should change
        if (shouldBeDelivered && !order.isDelivered()) {
            EStatus currentStatus = order.getStatus().getName();
            EStatus newStatus = findNextStatus(currentStatus);

            if (currentStatus == newStatus) {
                return false;
            }

            // Update order status
            setUpdateStatus(order, newStatus);
            DeliveryOrder saved = save(order);
            return saved.isDelivered();
        }

        return shouldBeDelivered;
    }

    private boolean areAllProductOrdersDeliveredOrCancelled(List<ProductOrder> productOrders) {
        if (productOrders == null || productOrders.isEmpty()) {
            return false;
        }

        // Check if all product orders are delivered or cancelled
        return productOrders.stream()
                .allMatch(productOrder -> productOrder.isDelivered() || productOrder.isCancelled());
    }

    @Override
    @Transactional
    public DeliveryOrder advanceOrderStatus(Long id) {
        DeliveryOrder existing = findById(id, false);
        DeliveryOrder order = super.advanceOrderStatus(id);

        // Check if the order status has really changed and is now in the PRODUCTION
        // state.
        if (!existing.getStatus().equals(order.getStatus()) && order.getStatus().getName() == EStatus.PRODUCTION) {
            // Update all product orders that are still in the PENDING state to PRODUCTION.
            order.getProductOrders()
                    .stream()
                    .filter(productOrder -> productOrder.getStatus().getName() == EStatus.PENDING)
                    .forEach(productOrder -> productOrderUseCase.setUpdateStatus(productOrder, EStatus.PRODUCTION));
        }

        save(order);
        return order;
    }

    /**
     * Handles the status change of a delivery order, this is trigger from
     * ProductOrderImpl.
     *
     * @param orderId   the ID of the order whose status has changed
     * @param newStatus the new status of the order
     */
    @Override
    @Transactional
    public void onStatusChanged(Long orderId, EStatus newStatus) {
        if (newStatus == EStatus.FINISHED) {
            isOrderFinished(orderId);
        } else if (newStatus == EStatus.DELIVERED) {
            isOrderDelivered(orderId);
        }
    }

    @Override
    @Transactional
    public DeliveryOrder deleteById(Long id) {
        log.debug("Attempting to delete DeliveryOrder with id: {}", id);

        DeliveryOrder deliveryOrder = findById(id, false);

        // Check if order can be deleted
        if (deliveryOrder.isFinished() || deliveryOrder.isDelivered()) {
            throw new IllegalStateException("Cannot delete finished or delivered orders");
        }

        try {
            // Delete related product orders first
            productOrderUseCase.deleteByDeliveryOrderId(id);

            // Delete the delivery order
            return delete(deliveryOrder);
        } catch (Exception e) {
            log.error("Error deleting DeliveryOrder with id: {}", id, e);
            throw new RuntimeException("Error deleting DeliveryOrder", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryOrderResponse> getOrdersForCustomerInvoicing(
            Long customerId,
            boolean isBilled,
            DateRange dateRange,
            PaginationCriteria pagination,
            boolean includeDeleted) {
        List<DeliveryOrder> orders;

        Sort sort = Sort.by(pagination.getSortOrders());
        Pageable paging = PageRequest.of(pagination.getPage(), pagination.getPageSize(),
                sort);

        List<Long> statusIdList = requestUtils.getBillableDeliveryOrderStatusIds();
        try (Session session = setDeleteFilter(includeDeleted)) {
            List<DeliveryOrderData> ordersData = repository.getOrdersForCustomerInvoicing(
                    customerId,
                    isBilled,
                    dateRange.startDate(),
                    dateRange.endDate(),
                    statusIdList,
                    paging);
            orders = mapper.toDomain(ordersData).stream().toList();
        }

        return orders.stream()
                .map(this::buildDeliveryOrderResponse)
                .toList();
    }

    private DeliveryOrderResponse buildDeliveryOrderResponse(DeliveryOrder order) {
        List<ProductOrder> productOrders = order.getProductOrders();

        return DeliveryOrderResponse.builder()
                .order(order)
                .pendingCount((int) productOrders.stream().filter(ProductOrder::isPending).count())
                .productionCount((int) productOrders.stream().filter(ProductOrder::isProduction).count())
                .finishedCount((int) productOrders.stream().filter(ProductOrder::isFinished).count())
                .deliveredCount((int) productOrders.stream().filter(ProductOrder::isDelivered).count())
                .cancelledCount((int) productOrders.stream().filter(ProductOrder::isCancelled).count())
                .pausedCount((int) productOrders.stream().filter(ProductOrder::isPaused).count())
                .build();
    }

    /*
     * Obtiene el Customer ID que se repite con mayor frecuencia en la lista de
     * órdenes
     */
    private Long getMostFrequentCustomerId(List<DeliveryOrder> orders) {
        // Crea un mapa para realizar un seguimiento del recuento de cada Customer ID
        Map<Long, Long> countMap = new HashMap<>();

        // Itera sobre la lista y cuenta la frecuencia de cada Customer ID
        for (DeliveryOrder order : orders) {
            Long customerId = order.getCustomer().getId();
            countMap.put(customerId, countMap.getOrDefault(customerId, 0L) + 1);
        }

        if (countMap.isEmpty()) {
            return 0L;
        } else if (countMap.size() == 1) {
            return countMap.keySet().iterator().next();
        }

        // Encuentra el Customer ID que se repite con mayor frecuencia y cuántas veces
        // se repite
        Long maxCount = 0L;
        Long mostFrequentCustomerId = 0L;
        for (Map.Entry<Long, Long> entry : countMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequentCustomerId = entry.getKey();
            }
        }

        return mostFrequentCustomerId;
    }

    /*
     * Obtiene las órdenes que se pasan en una lista de ids y filtra por el customer
     * que más se repite para entregar solo las órdenes del mismo customer
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeliveryOrderResponse> findByIdListAndDominantCustomer(
            List<Long> idList,
            List<Sort.Order> sortOrders,
            boolean includeDeleted) {
        List<DeliveryOrder> orders;

        try (Session session = setDeleteFilter(includeDeleted)) {
            orders = findByIdIn(idList, sortOrders, includeDeleted);
            Long dominantCustomerId = this.getMostFrequentCustomerId(orders);
            orders = orders.stream()
                    .filter(order -> order.getCustomer().getId().equals(dominantCustomerId))
                    .toList();
        }

        return orders.stream()
                .map(this::buildDeliveryOrderResponse)
                .toList();
    }
}
