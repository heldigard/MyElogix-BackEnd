package com.tarapaca.api.product_order.infrastructure.entry_point;

import static com.tarapaca.api.delivery_order.infrastructure.helper.DeliveryOrderHelper.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.generics.infrastructure.dto.ApiResponse;
import com.tarapaca.api.generics.infrastructure.entry_points.GenericProductionController;
import com.tarapaca.api.product_order.domain.gateway.ProductOrderGateway;
import com.tarapaca.api.product_order.domain.model.ProductOrder;
import com.tarapaca.api.product_order.domain.usecase.ProductOrderUseCase;

@RestController
@RequestMapping("/api/v1/product-order")
public class ProductOrderController
        extends GenericProductionController<ProductOrder, ProductOrderGateway, ProductOrderUseCase> {

    public ProductOrderController(ProductOrderUseCase useCase) {
        super(useCase);
    }

    @PutMapping("/status/advance/{id}")
    @PreAuthorize(UPDATE_DELIVERY_ORDER)
    @Transactional
    public ResponseEntity<ApiResponse<ProductOrder>> advanceOrderStatus(@PathVariable Long id) {
        try {
            ProductOrder order = useCase.advanceOrderStatus(id);
            return ResponseEntity.ok(new ApiResponse<ProductOrder>("Status transition successful", order));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<ProductOrder>("Failed to change status: " + e.getMessage()));
        }
    }

    @PutMapping("/status/advance/batch")
    @PreAuthorize(UPDATE_DELIVERY_ORDER)
    @Transactional
    public ResponseEntity<ApiResponse<ProductOrder>> advanceOrderStatus(@RequestBody List<Long> ids) {
        try {
            List<ProductOrder> orders = useCase.advanceOrderStatus(ids);
            return ResponseEntity.ok(new ApiResponse<ProductOrder>("Status transition successful", orders));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<ProductOrder>("Failed to change status: " + e.getMessage()));
        }
    }

    @GetMapping(value = "/isPaused")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Boolean> getIsPaused(
            @RequestParam Long id) {
        boolean isPaused = useCase.getIsPaused(id);

        return new ResponseEntity<>(isPaused, HttpStatus.OK);
    }
}
