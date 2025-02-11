package com.tarapaca.api.generics.infrastructure.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.tarapaca.api.generics.domain.model.GenericBasicEntity;
import com.tarapaca.api.generics.infrastructure.repository.GenericBasic.GenericBasicEntityData;

/**
 * Base mapper class for converting between domain entities and data entities
 *
 * @param <T> Domain entity type extending GenericBasicEntity
 * @param <D> Data entity type extending GenericBasicEntityData
 */
public class GenericBasicMapper<T extends GenericBasicEntity, D extends GenericBasicEntityData>
        implements GenericMapperGateway<T, D> {

    private final Class<T> domainClass;
    private final Class<D> dataClass;

    public GenericBasicMapper(Class<T> domainClass, Class<D> dataClass) {
        this.domainClass = domainClass;
        this.dataClass = dataClass;
    }

    /**
     * Maps data entity fields to domain entity
     *
     * @param source Data entity to map from
     * @param target Domain entity to map to
     */
    @Override
    @Nullable
    public T toDomain(@Nullable D source, @NonNull T target) {
        if (source == null) {
            return null;
        }
        Optional.ofNullable(source.getId()).ifPresent(target::setId);
        Optional.ofNullable(source.getVersion()).ifPresent(target::setVersion);
        target.setDeleted(source.isDeleted());
        return target;
    }

    @Override
    @Nullable
    public D toData(@Nullable T source, @NonNull D target) {
        if (source == null) {
            return null;
        }
        Optional.ofNullable(source.getId()).ifPresent(target::setId);
        Optional.ofNullable(source.getVersion()).ifPresent(target::setVersion);
        target.setDeleted(source.isDeleted());
        return target;
    }

    @Override
    @Nullable
    public T toDomain(@Nullable D source) {
        if (source == null) {
            return null;
        }
        return toDomain(source, createDomainInstance());
    }

    @Override
    @Nullable
    public D toData(@Nullable T source) {
        if (source == null) {
            return null;
        }
        return toData(source, createDataInstance());
    }

    @Override
    @Nullable
    public Collection<T> toDomain(@Nullable Collection<D> source) {
        if (source == null) {
            return Collections.emptyList();
        }

        Collection<T> target = source instanceof Set ? new HashSet<>() : new ArrayList<>();

        source.forEach(item -> target.add(toDomain(item)));
        return target;
    }

    @Override
    @Nullable
    public Collection<D> toData(@Nullable Collection<T> source) {
        if (source == null) {
            return Collections.emptyList();
        }

        Collection<D> target = source instanceof Set ? new HashSet<>() : new ArrayList<>();

        source.forEach(item -> target.add(toData(item)));
        return target;
    }

    protected T createDomainInstance() {
        try {
            return domainClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not create domain instance", e);
        }
    }

    protected D createDataInstance() {
        try {
            return dataClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not create data instance", e);
        }
    }
}
