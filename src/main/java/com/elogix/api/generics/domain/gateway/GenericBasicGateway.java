package com.elogix.api.generics.domain.gateway;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.elogix.api.generics.domain.model.GenericBasicEntity;
import com.elogix.api.generics.infrastructure.dto.PaginationResponse;
import com.elogix.api.shared.domain.model.pagination.PaginationCriteria;

/**
 * Base gateway interface that defines basic read operations
 *
 * @param <T> Type that extends GenericBasicEntity
 */
public interface GenericBasicGateway<T extends GenericBasicEntity> {
    void preserveExistingData(T newEntity, T existing);

    // Find single entity
    T findById(Long id, boolean includeDeleted);

    // Find multiple entities
    List<T> findByIdIn(List<Long> idList, List<Sort.Order> sortOrders, boolean includeDeleted);

    // Find all entities with sorting
    List<T> findAll(List<Sort.Order> sortOrders, boolean includeDeleted);

    // Find paginated results
    PaginationResponse<T> findAllPagination(
            PaginationCriteria pagination,
            boolean includeDeleted);
}
