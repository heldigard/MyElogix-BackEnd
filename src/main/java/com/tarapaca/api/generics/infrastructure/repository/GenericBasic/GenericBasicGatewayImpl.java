package com.tarapaca.api.generics.infrastructure.repository.GenericBasic;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tarapaca.api.generics.domain.gateway.GenericBasicGateway;
import com.tarapaca.api.generics.domain.model.GenericBasicEntity;
import com.tarapaca.api.generics.infrastructure.dto.PaginationResponse;
import com.tarapaca.api.generics.infrastructure.helpers.GenericMapperGateway;
import com.tarapaca.api.shared.domain.model.pagination.PaginationCriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;

/**
 * Base implementation of GenericBasicGateway that provides common CRUD
 * operations
 *
 * @param <T> Domain entity type
 * @param <D> Database entity type
 * @param <R> Repository type
 * @param <M> Mapper type
 */
@Slf4j
public abstract class GenericBasicGatewayImpl<T extends GenericBasicEntity, D extends GenericBasicEntityData, R extends JpaRepository<D, Long> & GenericBasicRepository<D>, M extends GenericMapperGateway<T, D>>
        implements GenericBasicGateway<T> {

    protected final R repository;
    protected final M mapper;
    protected final EntityManager entityManager;
    protected String deletedFilter;

    protected GenericBasicGatewayImpl(
            R repository,
            M mapper,
            EntityManager entityManager,
            String deletedFilter) {
        this.repository = repository;
        this.mapper = mapper;
        this.entityManager = entityManager;
        this.deletedFilter = deletedFilter;
    }

    protected Session setDeleteFilter(boolean includeDeleted) {
        if (deletedFilter == null || deletedFilter.isEmpty()) {
            throw new IllegalStateException("Delete filter name not configured");
        }

        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter(this.deletedFilter);
        filter.setParameter("isDeleted", includeDeleted);
        return session;
    }

    /**
     * Finds an entity by its ID with optional deleted records filtering
     *
     * @param id             The ID of the entity to find
     * @param includeDeleted Optional flag to include deleted records
     * @return The mapped domain entity
     * @throws IllegalArgumentException if id is null
     * @throws NoSuchElementException   if entity not found
     * @throws RuntimeException         if database error occurs
     */
    @Override
    @Transactional(readOnly = true)
    public T findById(Long id, boolean includeDeleted) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        try (Session session = setDeleteFilter(includeDeleted)) {
            D entity = ((JpaRepository<D, Long>) repository).findById(id)
                    .orElseThrow(() -> new NoSuchElementException(
                            String.format("Entity with id %d not found in repository %s",
                                    id, repository.getClass().getSimpleName())));

            return mapper.toDomain(entity);
        } catch (Exception e) {
            throw new RuntimeException(
                    String.format("Failed to find entity with id %d: %s", id, e.getMessage()), e);
        }
    }

    /**
     * Finds multiple entities by their IDs with optional sorting and deleted
     * records filtering
     *
     * @param ids            List of IDs to search for
     * @param sortOrders     Optional list of sort orders to apply
     * @param includeDeleted Flag to include deleted records
     * @return List of mapped domain entities matching the provided IDs
     * @throws IllegalArgumentException if idList is null
     * @throws RuntimeException         if database error occurs
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> findByIdIn(List<Long> ids, List<Sort.Order> sortOrders, boolean includeDeleted) {
        if (ids == null) {
            throw new IllegalArgumentException("IdList cannot be null");
        }

        if (ids.isEmpty()) {
            return List.of();
        }

        try (Session session = setDeleteFilter(includeDeleted)) {
            Sort sort = getDefaultSort(sortOrders);
            List<D> entities = repository.findByIdIn(ids, sort);

            return entities.stream()
                    .map(mapper::toDomain)
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException(
                    String.format("Failed to find entities", e.getMessage()), e);
        }
    }

    /**
     * Helper method to get sort configuration with defaults
     */
    public Sort getDefaultSort(List<Sort.Order> sortOrders) {
        if (sortOrders == null || sortOrders.isEmpty()) {
            return Sort.by(Sort.Direction.DESC, "id");
        }
        return Sort.by(sortOrders.stream()
                .map(order -> new Sort.Order(order.getDirection(), order.getProperty()))
                .toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll(List<Sort.Order> sortOrders, boolean includeDeleted) {
        try (Session session = setDeleteFilter(includeDeleted)) {
            Sort sort = getDefaultSort(sortOrders);
            List<D> entities = repository.findAll(sort);

            return mapper.toDomain(entities).stream().toList();
        } catch (Exception e) {
            log.error("Error finding all entities: {}", e.getMessage());
            throw new PersistenceException("Error finding all entities", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<T> findAllPagination(
            PaginationCriteria pagination,
            boolean includeDeleted) {
        // Execute paginated query with filter
        Page<D> pageResult;
        try (Session session = setDeleteFilter(includeDeleted)) {
            Sort sort = getDefaultSort(pagination.getSortOrders());
            Pageable paging = PageRequest.of(pagination.getPage(), pagination.getPageSize(), sort);
            pageResult = repository.findAll(paging);

            // Map results to response
            List<T> mappedResults = pageResult.getContent()
                    .stream()
                    .map(mapper::toDomain)
                    .toList();

            // Build pagination response
            return PaginationResponse.<T>builder()
                    .rowCount(pageResult.getTotalElements())
                    .rows(mappedResults)
                    .pagesCount(pageResult.getTotalPages())
                    .currentPage(pageResult.getNumber())
                    .success(!mappedResults.isEmpty())
                    .build();
        } catch (Exception e) {
            log.error("Error finding all entities: {}", e.getMessage());
            throw new PersistenceException("Error finding all entities", e);
        }
    }
}
