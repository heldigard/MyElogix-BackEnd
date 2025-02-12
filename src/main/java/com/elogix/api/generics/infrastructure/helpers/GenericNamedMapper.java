package com.elogix.api.generics.infrastructure.helpers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.elogix.api.generics.domain.model.GenericNamed;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedData;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;

/**
 * Generic mapper for named entities that extends entity mapper functionality
 *
 * @param <D> Domain entity type extending GenericNamed
 * @param <E> Data entity type extending GenericNamedData
 */
public class GenericNamedMapper<D extends GenericNamed, E extends GenericNamedData>
        extends GenericEntityMapper<D, E> {

    protected GenericNamedMapper(@NonNull Class<D> domainClass, @NonNull Class<E> dataClass,
            @NonNull UserBasicMapper userMapper) {
        super(domainClass, dataClass, userMapper);
    }

    @Override
    @Nullable
    public D toDomain(@Nullable E source, @NonNull D target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);
        Optional.ofNullable(source.getName()).ifPresent(target::setName);
        return target;
    }

    @Override
    @Nullable
    public E toData(@Nullable D source, @NonNull E target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);
        Optional.ofNullable(source.getName()).ifPresent(target::setName);
        return target;
    }
}
