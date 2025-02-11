package com.tarapaca.api.generics.infrastructure.entry_points;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tarapaca.api.generics.domain.gateway.GenericGateway;
import com.tarapaca.api.generics.domain.gateway.GenericNamedGateway;
import com.tarapaca.api.generics.domain.model.GenericNamed;
import com.tarapaca.api.generics.domain.usecase.GenericNamedUseCase;
import com.tarapaca.api.generics.infrastructure.dto.ApiResponse;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GenericNamedController<T extends GenericNamed, G extends GenericNamedGateway<T> & GenericGateway<T>, U extends GenericNamedUseCase<T, G>>
        extends GenericController<T, G, U> {

    protected GenericNamedController(U useCase) {
        super(useCase);
    }

    @DeleteMapping("/name/{name}")
    @PreAuthorize("hasAnyAuthority('manager:*', 'admin:*')")
    public ResponseEntity<ApiResponse<T>> deleteByName(@PathVariable String name, boolean includeDeleted) {
        try {
            log.info("Deleting entity with name: {}", name);
            T entity = useCase.deleteByName(name);
            return ResponseEntity.ok(new ApiResponse<T>("Entity deleted successfully", entity));
        } catch (Exception e) {
            log.error("Error deleting entity: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error deleting entity: " + e.getMessage()));
        }
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
}
