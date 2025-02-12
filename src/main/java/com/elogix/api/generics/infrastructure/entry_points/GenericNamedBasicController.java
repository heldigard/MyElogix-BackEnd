package com.elogix.api.generics.infrastructure.entry_points;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elogix.api.generics.domain.gateway.GenericNamedBasicGateway;
import com.elogix.api.generics.domain.model.GenericNamedBasic;
import com.elogix.api.generics.domain.usecase.GenericNamedBasicUseCase;
import com.elogix.api.generics.infrastructure.dto.ApiResponse;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract base controller for handling named basic entities.
 * Extends GenericBasicController with additional name-based operations.
 *
 * @param <T> The domain model type that extends GenericNamedBasic
 * @param <G> The gateway type that extends GenericNamedBasicGateway<T>
 * @param <U> The use case type extending GenericNamedBasicUseCase for handling
 *            business logic
 */
@Slf4j
public abstract class GenericNamedBasicController<T extends GenericNamedBasic, G extends GenericNamedBasicGateway<T>, U extends GenericNamedBasicUseCase<T, G>>
        extends GenericBasicController<T, G, U> {

    protected GenericNamedBasicController(U useCase) {
        super(useCase);
    }

    @GetMapping("/search/name")
    public ResponseEntity<ApiResponse<T>> findByName(
            @NotBlank @RequestParam String name,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {
        try {
            log.info("Searching entity by name: {} (includeDeleted: {})", name, includeDeleted);
            T dato = useCase.findByName(name, includeDeleted);

            if (dato == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Entity not found with name: " + name, null));
            }

            return ResponseEntity.ok(new ApiResponse<>("Entity found successfully", dato));
        } catch (Exception e) {
            log.error("Error searching entity by name: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error searching entity: " + e.getMessage(), null));
        }
    }

    @GetMapping("/exists/name")
    public ResponseEntity<ApiResponse<Boolean>> existsByName(
            @NotBlank @RequestParam String name,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {
        try {
            log.info("Checking if entity exists by name: {} (includeDeleted: {})", name, includeDeleted);
            boolean exists = useCase.existsByName(name, includeDeleted);
            return ResponseEntity.ok(new ApiResponse<>("Check completed", exists));
        } catch (Exception e) {
            log.error("Error checking entity existence by name: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error checking entity: " + e.getMessage(), null));
        }
    }
}
