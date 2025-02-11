package com.tarapaca.api.generics.infrastructure.entry_points;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.tarapaca.api.generics.domain.gateway.GenericProductionGateway;
import com.tarapaca.api.generics.domain.model.GenericProduction;
import com.tarapaca.api.generics.domain.usecase.GenericProductionUseCase;
import com.tarapaca.api.generics.infrastructure.dto.ApiResponse;

import jakarta.validation.constraints.NotNull;

public abstract class GenericProductionController<T extends GenericProduction, G extends GenericProductionGateway<T>, U extends GenericProductionUseCase<T, G>>
        extends GenericStatusController<T, G, U> {
    private static final String STATUS_UPDATED_SUCCESSFULLY = "Status updated successfully";

    protected GenericProductionController(U useCase) {
        super(useCase);
    }

    @PutMapping("/{id}/status/cancelled")
    public ResponseEntity<ApiResponse<T>> toggleCancelled(@PathVariable @NotNull Long id) {
        try {
            T result = useCase.toggleIsCancelled(id);
            return ResponseEntity.ok(new ApiResponse<T>(
                    STATUS_UPDATED_SUCCESSFULLY,
                    result));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<T>("Failed to change status: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/status/paused")
    public ResponseEntity<ApiResponse<T>> togglePaused(@PathVariable @NotNull Long id) {
        try {
            T result = useCase.toggleIsPaused(id);
            return ResponseEntity.ok(new ApiResponse<T>(
                    STATUS_UPDATED_SUCCESSFULLY,
                    result));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<T>("Failed to change status: " + e.getMessage()));
        }
    }
}
