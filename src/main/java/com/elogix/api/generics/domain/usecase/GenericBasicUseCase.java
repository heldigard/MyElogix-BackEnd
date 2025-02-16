package com.elogix.api.generics.domain.usecase;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.elogix.api.generics.domain.gateway.GenericBasicGateway;
import com.elogix.api.generics.domain.model.GenericBasicEntity;
import com.elogix.api.generics.infrastructure.dto.PaginationResponse;
import com.elogix.api.shared.domain.model.pagination.PaginationCriteria;

import lombok.RequiredArgsConstructor;

/**
 * Abstract base class for basic use cases providing common CRUD operations.
 *
 * @param <T> The domain model type that extends GenericBasicEntity,
 *            representing
 *            the basic entity being managed
 * @param <G> The gateway type that extends GenericBasicGateway<T>, providing
 *            data access and persistence operations
 */
@Service
@RequiredArgsConstructor
public abstract class GenericBasicUseCase<T extends GenericBasicEntity, G extends GenericBasicGateway<T>> {

    protected final G gateway;

    public T findById(Long id, boolean includeDeleted) {
        return gateway.findById(id, includeDeleted);
    }

    public List<T> findByIdIn(List<Long> ids,
            List<Sort.Order> sortOrders,
            boolean includeDeleted) {
        return gateway.findByIdIn(ids, sortOrders, includeDeleted);
    }

    public List<T> findAll(List<Sort.Order> sortOrders, boolean includeDeleted) {
        return gateway.findAll(sortOrders, includeDeleted);
    }

    public PaginationResponse<T> findAllPagination(PaginationCriteria pagination,
            boolean includeDeleted) {
        return gateway.findAllPagination(pagination, includeDeleted);
    }
}
