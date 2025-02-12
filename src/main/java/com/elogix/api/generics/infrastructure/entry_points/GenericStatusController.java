package com.elogix.api.generics.infrastructure.entry_points;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elogix.api.generics.domain.gateway.GenericStatusGateway;
import com.elogix.api.generics.domain.model.GenericStatus;
import com.elogix.api.generics.domain.usecase.GenericStatusUseCase;
import com.elogix.api.generics.infrastructure.dto.ApiResponse;
import com.elogix.api.generics.infrastructure.exception.ResourceNotFoundException;

import jakarta.validation.constraints.NotNull;

public abstract class GenericStatusController<T extends GenericStatus, G extends GenericStatusGateway<T>, U extends GenericStatusUseCase<T, G>>
        extends GenericController<T, G, U> {

    protected GenericStatusController(U useCase) {
        super(useCase);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('operator:*', 'manager:*', 'admin:*')")
    public ResponseEntity<ApiResponse<T>> updateStatus(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull String status) {
        try {
            T updatedEntity = useCase.updateStatus(id, status);
            return ResponseEntity.ok(new ApiResponse<>("Status updated successfully", updatedEntity));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Resource not found"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(e.getMessage()));
        }
    }
}
