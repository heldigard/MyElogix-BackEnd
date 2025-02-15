package com.elogix.api.delivery_order.infrastructure.entry_point;

import static com.elogix.api.delivery_order.infrastructure.helper.DeliveryOrderHelper.*;

import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.elogix.api.delivery_order.domain.gateway.DeliveryOrderGateway;
import com.elogix.api.delivery_order.domain.model.DeliveryOrder;
import com.elogix.api.delivery_order.domain.usecase.DeliveryOrderUseCase;
import com.elogix.api.delivery_order.dto.DeliveryOrderResponse;
import com.elogix.api.generics.infrastructure.dto.ApiResponse;
import com.elogix.api.generics.infrastructure.entry_points.GenericProductionController;
import com.elogix.api.product_order.domain.model.ProductOrder;
import com.elogix.api.shared.domain.model.pagination.DateRange;
import com.elogix.api.shared.domain.model.pagination.PaginationCriteria;
import com.elogix.api.shared.infraestructure.helpers.DateUtils;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/delivery-order")
public class DeliveryOrderController
        extends GenericProductionController<DeliveryOrder, DeliveryOrderGateway, DeliveryOrderUseCase> {

    public DeliveryOrderController(DeliveryOrderUseCase useCase) {
        super(useCase);
    }

    @GetMapping(value = "/nuevo/{customerId}")
    @PreAuthorize(CREATE_DELIVERY_ORDER)
    public ResponseEntity<ApiResponse<DeliveryOrder>> nuevo(
            @PathVariable Long customerId) {
        try {
            if (customerId <= 0) {
                throw new IllegalArgumentException("Invalid customer ID");
            }

            DeliveryOrder order = useCase.nuevo(customerId);
            ApiResponse<DeliveryOrder> response = new ApiResponse<>(
                    "Delivery order created successfully",
                    order);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error creating delivery order");
        }
    }

    @Override
    @PostMapping
    @PreAuthorize(CREATE_DELIVERY_ORDER)
    @Transactional
    public ResponseEntity<ApiResponse<DeliveryOrder>> add(
            @RequestBody DeliveryOrder order) {
        return super.add(order);
    }

    @Override
    @PreAuthorize(UPDATE_DELIVERY_ORDER)
    @Transactional
    @PutMapping
    public ResponseEntity<ApiResponse<DeliveryOrder>> update(@RequestBody DeliveryOrder order) {
        return super.update(order);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize(DELETE_DELIVERY_ORDER)
    @Transactional
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id) {
        return super.deleteById(id);
    }

    @PutMapping("/status/advance/{id}")
    @PreAuthorize(UPDATE_DELIVERY_ORDER)
    @Transactional
    public ResponseEntity<ApiResponse<DeliveryOrder>> advanceOrderStatus(@PathVariable Long id) {
        try {
            DeliveryOrder order = useCase.advanceOrderStatus(id);
            return ResponseEntity.ok(new ApiResponse<DeliveryOrder>("Status transition successful", order));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<DeliveryOrder>("Failed to change status: " + e.getMessage()));
        }
    }

    @PutMapping("/billing-status/{id}")
    @PreAuthorize(UPDATE_DELIVERY_ORDER)
    @Transactional
    public ResponseEntity<ApiResponse<DeliveryOrder>> updateIsBilled(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") boolean isBilled) {
        try {
            DeliveryOrder response = useCase.updateIsBilled(id, isBilled);
            return ResponseEntity.ok(new ApiResponse<DeliveryOrder>(
                    "Billing status updated successfully",
                    response));
        } catch (Exception e) {
            log.error("Error updating billing status for order {}: {}", id, e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse<DeliveryOrder>("Failed to update billing status: " + e.getMessage()));
        }
    }

    @PutMapping("/billing-status/batch")
    @PreAuthorize(UPDATE_DELIVERY_ORDER)
    @Transactional
    public ResponseEntity<ApiResponse<DeliveryOrder>> updateBatchBillingStatus(
            @RequestParam List<Long> ids,
            @RequestParam(required = false, defaultValue = "false") boolean isBilled) {
        try {
            List<DeliveryOrder> response = useCase.updateIsBilled(ids, isBilled);
            return ResponseEntity.ok(new ApiResponse<DeliveryOrder>(
                    String.format("Successfully updated %d orders", response.size()),
                    response));

        } catch (Exception e) {
            log.error("Error updating batch billing status: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse<DeliveryOrder>(
                            "Failed to update billing status: " + e.getMessage()));
        }
    }

    @GetMapping(value = "/{id}/response")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<DeliveryOrderResponse>> findByIdResponse(@PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {
        try {
            DeliveryOrder order = useCase.findById(id, includeDeleted);
            DeliveryOrderResponse response = DeliveryOrderResponse.builder().order(order)
                    .pendingCount((int) order.getProductOrders().stream()
                            .filter(ProductOrder::isPending).count())
                    .productionCount((int) order.getProductOrders().stream()
                            .filter(ProductOrder::isProduction).count())
                    .finishedCount((int) order.getProductOrders().stream()
                            .filter(ProductOrder::isFinished).count())
                    .deliveredCount((int) order.getProductOrders().stream()
                            .filter(ProductOrder::isDelivered).count())
                    .cancelledCount((int) order.getProductOrders().stream()
                            .filter(ProductOrder::isCancelled).count())
                    .pausedCount((int) order.getProductOrders().stream()
                            .filter(ProductOrder::isPaused).count())
                    .build();
            return ResponseEntity.ok(new ApiResponse<DeliveryOrderResponse>("Order retrieved successfully", response));
        } catch (IllegalArgumentException e) {
            log.error("Invalid request parameters: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<DeliveryOrderResponse>("Invalid request parameters: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting delivery order", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<DeliveryOrderResponse>("An unexpected error occurred"));
        }
    }

    @GetMapping("/orders/invoicing-by-date-range/{customerId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<DeliveryOrderResponse>> getOrdersForCustomerInvoicing(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false) List<String> properties,
            @RequestParam(required = false) List<String> directions,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted,
            @RequestParam String createdAtStart,
            @RequestParam String createdAtEnd,
            @PathVariable Long customerId,
            @RequestParam(required = false, defaultValue = "false") boolean isBilled) {
        try {
            DateRange dateRange = new DateRange(
                    DateUtils.parseDateTime(createdAtStart),
                    DateUtils.parseDateTime(createdAtEnd));

            List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(properties, directions);
            PaginationCriteria pagination = new PaginationCriteria(
                    page,
                    pageSize,
                    sortOrders);

            List<DeliveryOrderResponse> response = useCase
                    .getOrdersForCustomerInvoicing(
                            customerId,
                            isBilled,
                            dateRange,
                            pagination,
                            includeDeleted);

            return ResponseEntity.ok(new ApiResponse<DeliveryOrderResponse>("Orders retrieved successfully", response));
        } catch (IllegalArgumentException e) {
            log.error("Invalid request parameters: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<DeliveryOrderResponse>("Invalid request parameters: " + e.getMessage()));
        } catch (DateTimeParseException e) {
            log.error("Invalid date format: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<DeliveryOrderResponse>("Invalid date format: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting customer billing orders", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<DeliveryOrderResponse>("An unexpected error occurred"));
        }
    }

    @GetMapping("/orders/invoicing-by-dominant-user")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<DeliveryOrderResponse>> findByIdListAndDominantCustomer(
            @RequestParam List<Long> ids,
            @RequestParam(required = false) List<String> properties,
            @RequestParam(required = false) List<String> directions,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {
        try {
            List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(properties, directions);
            List<DeliveryOrderResponse> response = useCase
                    .findByIdListAndDominantCustomer(
                            ids,
                            sortOrders,
                            includeDeleted);

            return ResponseEntity.ok(new ApiResponse<DeliveryOrderResponse>("Orders retrieved successfully", response));
        } catch (IllegalArgumentException e) {
            log.error("Invalid request parameters: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<DeliveryOrderResponse>(false,
                            "Invalid request parameters: " + e.getMessage()));
        } catch (DateTimeParseException e) {
            log.error("Invalid date format: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<DeliveryOrderResponse>(false, "Invalid date format: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting customer billing orders", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<DeliveryOrderResponse>(false, "An unexpected error occurred"));
        }
    }
}
