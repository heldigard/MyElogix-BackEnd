package com.elogix.api.generics.infrastructure.entry_points;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.elogix.api.generics.domain.gateway.GenericBasicGateway;
import com.elogix.api.generics.domain.model.GenericBasicEntity;
import com.elogix.api.generics.domain.usecase.GenericBasicUseCase;
import com.elogix.api.generics.infrastructure.dto.ApiResponse;
import com.elogix.api.generics.infrastructure.dto.PaginationResponse;
import com.elogix.api.shared.domain.model.pagination.PaginationCriteria;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract base controller providing basic CRUD operations through REST
 * endpoints.
 *
 * @param <T> The entity type extending GenericBasicEntity
 * @param <G> The gateway type extending GenericBasicGateway for data access
 * @param <U> The use case type extending GenericBasicUseCase for handling
 *            business logic
 */
@Slf4j
@RequiredArgsConstructor
public abstract class GenericBasicController<T extends GenericBasicEntity, G extends GenericBasicGateway<T>, U extends GenericBasicUseCase<T, G>> {

    protected final U useCase;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<T>> findById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {
        if (id == null) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<T>(false, "ID cannot be null"));
        }

        T entity = useCase.findById(id, includeDeleted);
        if (entity == null) {
            return ResponseEntity.ok(
                    new ApiResponse<T>(false, "Entity not found"));
        }

        return ResponseEntity.ok(
                new ApiResponse<T>("Entity found successfully", entity));
    }

    @GetMapping("/batch")
    public ResponseEntity<ApiResponse<T>> findByIdIn(
            @RequestParam List<Long> ids,
            @RequestParam(required = false) List<String> properties,
            @RequestParam(required = false) List<String> directions,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("ID cannot be null"));
        }

        List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(properties, directions);
        List<T> entities = useCase.findByIdIn(ids, sortOrders, includeDeleted);
        if (entities == null || entities.isEmpty()) {
            return ResponseEntity.ok(
                    new ApiResponse<T>("Entities not found"));
        }

        return ResponseEntity.ok(
                new ApiResponse<T>("Entities retrieved successfully", entities));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<ApiResponse<T>> findAll(
            @RequestParam(required = false) List<String> properties,
            @RequestParam(required = false) List<String> directions,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {
        List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(properties, directions);
        List<T> entities = useCase.findAll(sortOrders, includeDeleted);
        if (entities == null || entities.isEmpty()) {
            return ResponseEntity.ok(
                    new ApiResponse<T>("Entities not found"));
        }

        return ResponseEntity.ok(
                new ApiResponse<T>("Entities retrieved successfully", entities));
    }

    @GetMapping(value = "/pagination")
    public ResponseEntity<PaginationResponse<T>> findAllPagination(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false) List<String> properties,
            @RequestParam(required = false) List<String> directions,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {
        try {
            List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(properties, directions);
            PaginationCriteria pagination = new PaginationCriteria(
                    page,
                    pageSize,
                    sortOrders);

            PaginationResponse<T> pageRecords = useCase.findAllPagination(pagination,
                    includeDeleted);

            if (!pageRecords.isSuccess()) {
                pageRecords.setMessage("Records not found");
                return ResponseEntity.ok(pageRecords);
            }

            pageRecords.setMessage("Records retrieved successfully");
            return ResponseEntity.ok(pageRecords);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(PaginationResponse.<T>builder()
                            .success(false)
                            .message(e.getMessage())
                            .build());
        }
    }
}
