package com.tarapaca.api.generics.infrastructure.helpers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.tarapaca.api.generics.domain.model.GenericEntity;
import com.tarapaca.api.generics.infrastructure.repository.GenericEntity.GenericEntityData;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserBasicMapper;

/**
 * Generic mapper for entities with audit information
 *
 * @param <T> Domain entity type extending GenericEntity
 * @param <D> Data entity type extending GenericEntityData
 */
public class GenericEntityMapper<T extends GenericEntity, D extends GenericEntityData>
        extends GenericBasicMapper<T, D> {

    private final UserBasicMapper userMapper;

    protected GenericEntityMapper(@NonNull Class<T> domainClass, @NonNull Class<D> dataClass,
            @NonNull UserBasicMapper userMapper) {
        super(domainClass, dataClass);
        this.userMapper = userMapper;
    }

    /**
     * Maps data entity fields to domain entity including audit information
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
        super.toDomain(source, target);
        Optional.ofNullable(source.getCreatedAt()).ifPresent(target::setCreatedAt);
        Optional.ofNullable(source.getUpdatedAt()).ifPresent(target::setUpdatedAt);
        Optional.ofNullable(source.getDeletedAt()).ifPresent(target::setDeletedAt);
        Optional.ofNullable(source.getCreatedBy()).map(userMapper::toDomain).ifPresent(target::setCreatedBy);
        Optional.ofNullable(source.getUpdatedBy()).map(userMapper::toDomain).ifPresent(target::setUpdatedBy);
        Optional.ofNullable(source.getDeletedBy()).map(userMapper::toDomain).ifPresent(target::setDeletedBy);
        return target;
    }

    /**
     * Maps domain entity fields to data entity including audit information
     *
     * @param source Domain entity to map from
     * @param target Data entity to map to
     */
    @Override
    @Nullable
    public D toData(@Nullable T source, @NonNull D target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);
        Optional.ofNullable(source.getCreatedAt()).ifPresent(target::setCreatedAt);
        Optional.ofNullable(source.getUpdatedAt()).ifPresent(target::setUpdatedAt);
        Optional.ofNullable(source.getDeletedAt()).ifPresent(target::setDeletedAt);
        Optional.ofNullable(source.getCreatedBy()).map(userMapper::toData).ifPresent(target::setCreatedBy);
        Optional.ofNullable(source.getUpdatedBy()).map(userMapper::toData).ifPresent(target::setUpdatedBy);
        Optional.ofNullable(source.getDeletedBy()).map(userMapper::toData).ifPresent(target::setDeletedBy);
        return target;
    }
}
