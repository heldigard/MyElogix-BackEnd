package com.elogix.api.generics.infrastructure.helpers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.elogix.api.generics.domain.model.GenericNamedBasic;
import com.elogix.api.generics.infrastructure.repository.GenericNamedBasic.GenericNamedBasicData;

/**
 * Generic mapper for entities with basic naming support
 *
 * @param <D> Domain entity type extending GenericNamedBasic
 * @param <E> Data entity type extending GenericNamedBasicData
 */
public class GenericNamedBasicMapper<D extends GenericNamedBasic, E extends GenericNamedBasicData>
        extends GenericBasicMapper<D, E> {

    /**
     * Constructor for GenericNamedBasicMapper
     *
     * @param domainClass Domain entity class
     * @param dataClass   Data entity class
     */
    protected GenericNamedBasicMapper(@NonNull Class<D> domainClass, @NonNull Class<E> dataClass) {
        super(domainClass, dataClass);
    }

    /**
     * Maps data entity fields to domain entity including name
     *
     * @param source Data entity to map from
     * @param target Domain entity to map to
     */
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

    /**
     * Maps domain entity fields to data entity including name
     *
     * @param source Domain entity to map from
     * @param target Data entity to map to
     */
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
