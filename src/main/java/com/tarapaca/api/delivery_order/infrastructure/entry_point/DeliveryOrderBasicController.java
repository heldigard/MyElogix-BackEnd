package com.tarapaca.api.delivery_order.infrastructure.entry_point;

import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.delivery_order.domain.gateway.DeliveryOrderBasicGateway;
import com.tarapaca.api.delivery_order.domain.model.DeliveryOrderBasic;
import com.tarapaca.api.delivery_order.domain.usecase.DeliveryOrderBasicUseCase;
import com.tarapaca.api.delivery_order.dto.CustomerOrdersSummaryDTO;
import com.tarapaca.api.generics.infrastructure.dto.ApiResponse;
import com.tarapaca.api.generics.infrastructure.dto.PaginationResponse;
import com.tarapaca.api.generics.infrastructure.entry_points.GenericBasicController;
import com.tarapaca.api.shared.domain.model.pagination.DateRange;
import com.tarapaca.api.shared.domain.model.pagination.PaginationCriteria;
import com.tarapaca.api.shared.infraestructure.helpers.DateUtils;
import com.tarapaca.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/delivery-order-basic")
public class DeliveryOrderBasicController
        extends
        GenericBasicController<DeliveryOrderBasic, DeliveryOrderBasicGateway, DeliveryOrderBasicUseCase> {
    private static final String ORDERS_RETRIEVED_SUCCESSFULLY = "Orders retrieved successfully";
    private static final String NO_ORDERS_FOUND = "No orders found";

    public DeliveryOrderBasicController(DeliveryOrderBasicUseCase useCase) {
        super(useCase);
    }

    @GetMapping("/orders/invoicing-by-date-range")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<DeliveryOrderBasic>> getOrdersForInvoicing(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false) List<String> properties,
            @RequestParam(required = false) List<String> directions,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            DateRange dateRange = new DateRange(
                    DateUtils.parseDateTime(startDate),
                    DateUtils.parseDateTime(endDate));

            List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(properties, directions);
            PaginationCriteria pagination = new PaginationCriteria(
                    page,
                    pageSize,
                    sortOrders);

            List<DeliveryOrderBasic> response = useCase
                    .getOrdersForInvoicing(
                            dateRange,
                            pagination,
                            includeDeleted);

            return ResponseEntity.ok(new ApiResponse<>("Orders retrieved successfully", response));
        } catch (IllegalArgumentException e) {
            log.error("Invalid request parameters: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false,
                            "Invalid request parameters: " + e.getMessage()));
        } catch (DateTimeParseException e) {
            log.error("Invalid date format: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Invalid date format: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting customer billing orders", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>(false, "An unexpected error occurred"));
        }
    }

    @PostMapping(value = "/filter-by-status")
    public ResponseEntity<PaginationResponse<DeliveryOrderBasic>> getByStatusFiltered(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false) List<String> properties,
            @RequestParam(required = false) List<String> directions,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted,
            @RequestBody List<Long> statusIdList) {
        try {
            List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(properties, directions);
            PaginationCriteria pagination = new PaginationCriteria(
                    page,
                    pageSize,
                    sortOrders);
            PaginationResponse<DeliveryOrderBasic> pageOrders = useCase.getByStatusFiltered(statusIdList,
                    pagination,
                    includeDeleted);

            if (!pageOrders.isSuccess()) {
                pageOrders.setMessage(NO_ORDERS_FOUND);
                return ResponseEntity.ok(pageOrders);
            }

            pageOrders.setMessage(ORDERS_RETRIEVED_SUCCESSFULLY);
            return ResponseEntity.ok(pageOrders);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/filter-by-status-and-date")
    public ResponseEntity<ApiResponse<DeliveryOrderBasic>> getByStatusAndDateRange(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false) List<String> properties,
            @RequestParam(required = false) List<String> directions,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam List<Long> statusIdList) {
        try {
            DateRange dateRange = DateUtils.parseDateRange(startDate, endDate);

            List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(properties, directions);
            PaginationCriteria pagination = new PaginationCriteria(
                    page,
                    pageSize,
                    sortOrders);

            List<DeliveryOrderBasic> orders = useCase.getByStatusAndDateRange(
                    statusIdList,
                    dateRange,
                    pagination,
                    includeDeleted);

            if (orders.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(NO_ORDERS_FOUND));
            }

            return ResponseEntity.ok(
                    new ApiResponse<>(ORDERS_RETRIEVED_SUCCESSFULLY, orders));
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Consulta las órdenes sin facturar, agrupadas por cliente y billed,
     * basado en la fecha de creación
     */
    @GetMapping("/orders/billed/summary")
    public ResponseEntity<ApiResponse<CustomerOrdersSummaryDTO>> findCustomerOrdersSummary(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false) List<String> properties,
            @RequestParam(required = false) List<String> directions,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false, defaultValue = "false") boolean isBilled) {
        try {
            DateRange dateRange = DateUtils.parseDateRange(startDate, endDate);

            List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(properties, directions);
            PaginationCriteria pagination = new PaginationCriteria(
                    page,
                    pageSize,
                    sortOrders);

            List<CustomerOrdersSummaryDTO> orders = useCase
                    .findCustomerOrdersSummary(
                            dateRange,
                            isBilled,
                            pagination,
                            includeDeleted);

            if (orders.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(NO_ORDERS_FOUND));
            }

            return ResponseEntity.ok(
                    new ApiResponse<>(ORDERS_RETRIEVED_SUCCESSFULLY, orders));
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
