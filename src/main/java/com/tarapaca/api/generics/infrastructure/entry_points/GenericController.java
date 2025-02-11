
package com.tarapaca.api.generics.infrastructure.entry_points;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tarapaca.api.generics.domain.gateway.GenericGateway;
import com.tarapaca.api.generics.domain.model.GenericEntity;
import com.tarapaca.api.generics.domain.usecase.GenericUseCase;
import com.tarapaca.api.generics.infrastructure.dto.ApiResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * GenericController is an abstract controller that provides basic CRUD
 * operations for entities of type T.
 *
 * @param <T> the type of the entity
 * @param <G> the type of the gateway
 * @param <U> the type of the use case
 */
@Slf4j
public abstract class GenericController<T extends GenericEntity, G extends GenericGateway<T>, U extends GenericUseCase<T, G>>
        extends GenericBasicController<T, G, U> {

    /**
     * Constructs a new GenericController with the specified use case.
     *
     * @param useCase the use case to be used by this controller
     */
    protected GenericController(U useCase) {
        super(useCase);
    }

    /**
     * Adds a new entity.
     *
     * @param dato the entity to be added
     * @return a ResponseEntity containing the ApiResponse with the created entity
     */
    @PostMapping
    public ResponseEntity<ApiResponse<T>> add(@RequestBody T dato) {
        try {
            log.info("Creating new entity: {}", dato);
            T newDato = useCase.add(dato);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("Entity created successfully", newDato));
        } catch (Exception e) {
            log.error("Error creating entity: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error creating entity: " + e.getMessage()));
        }
    }

    /**
     * Updates an existing entity with the given ID.
     *
     * @param id   the ID of the entity to update
     * @param dato the updated entity data
     * @return a ResponseEntity containing the ApiResponse with the updated entity
     */
    @PutMapping
    public ResponseEntity<ApiResponse<T>> update(@RequestBody T dato) {
        try {
            T updateDato = useCase.update(dato);
            return ResponseEntity.ok(new ApiResponse<T>("Entity updated successfully", updateDato));
        } catch (Exception e) {
            log.error("Error updating entity: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<T>("Error updating entity: " + e.getMessage()));
        }
    }

    /**
     * Deletes the entity with the specified ID.
     *
     * @param id the ID of the entity to delete
     * @return a ResponseEntity containing the ApiResponse indicating the result of
     *         the deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id) {
        try {
            log.info("Deleting entity with id: {}", id);
            useCase.deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Entity deleted successfully"));
        } catch (Exception e) {
            log.error("Error deleting entity: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error deleting entity: " + e.getMessage()));
        }
    }
}
