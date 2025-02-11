package com.tarapaca.api.generics.infrastructure.repository.GenericNamed;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tarapaca.api.generics.domain.gateway.GenericNamedGateway;
import com.tarapaca.api.generics.domain.model.GenericNamed;
import com.tarapaca.api.generics.infrastructure.helpers.GenericMapperGateway;
import com.tarapaca.api.generics.infrastructure.repository.GenericEntity.GenericGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

/**
 * Generic implementation for repositories that handle named entities
 * Extends basic CRUD operations and adds name-specific functionality
 *
 * @param <T> Domain entity type extending GenericNamed
 * @param <D> Data entity type extending GenericNamedData
 * @param <R> Repository type extending JPA and custom repositories
 * @param <M> Mapper type for converting between domain and data entities
 */
public abstract class GenericNamedGatewayImpl<T extends GenericNamed, D extends GenericNamedData & Serializable, R extends GenericNamedRepository<D>, M extends GenericMapperGateway<T, D>>
        extends GenericGatewayImpl<T, D, R, M>
        implements GenericNamedGateway<T> {

    protected GenericNamedGatewayImpl(
            R repository,
            M mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
    }

    @Override
    @Transactional(readOnly = true)
    public T findByName(String name, boolean includeDeleted) {
        Assert.hasText(name, "Name must not be empty");

        try (Session session = setDeleteFilter(includeDeleted)) {
            Optional<D> entity = repository.findByName(name);
            return mapper.toDomain(
                    entity.orElseThrow(() -> new NoSuchElementException("Entity not found with name: " + name)));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public T deleteByName(String name) {
        Assert.hasText(name, "Name must not be empty");
        T entity = findByName(name, false);
        return delete(entity);

    }

    @Override
    public boolean existsByName(String name, boolean includeDeleted) {
        try (Session session = setDeleteFilter(includeDeleted)) {
            return repository.existsByName(name);
        }
    }

    public List<T> findByNameLike(String name, boolean includeDeleted) {
        try (Session session = setDeleteFilter(includeDeleted)) {
            List<D> dataList = repository.findByNameLike(name);
            return dataList.stream()
                    .map(mapper::toDomain)
                    .toList();
        }
    }

    public List<T> findByNameContaining(String name, boolean includeDeleted) {
        try (Session session = setDeleteFilter(includeDeleted)) {
            List<D> dataList = repository.findByNameContaining(name);
            return dataList.stream()
                    .map(mapper::toDomain)
                    .toList();
        }
    }
}
