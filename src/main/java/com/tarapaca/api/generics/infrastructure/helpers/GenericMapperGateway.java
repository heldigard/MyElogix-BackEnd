package com.tarapaca.api.generics.infrastructure.helpers;

import java.util.Collection;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Generic interface for mapping between domain and data entities
 *
 * @param <T> Domain entity type
 * @param <D> Data entity type
 */
public interface GenericMapperGateway<T, D> {
    /**
     * Converts a data entity to a domain entity
     *
     * @param data Data entity to convert
     * @return Domain entity instance
     */
    @Nullable
    T toDomain(@Nullable D source, @NonNull T target);

    /**
     * Converts a domain entity to a data entity
     *
     * @param domain Domain entity to convert
     * @return Data entity instance
     */
    @Nullable
    D toData(@Nullable T source, @NonNull D target);

    /**
     * Converts a data entity to a domain entity
     *
     * @param data Data entity to convert
     * @return Domain entity instance
     */
    @Nullable
    T toDomain(@Nullable D source);

    /**
     * Converts a domain entity to a data entity
     *
     * @param domain Domain entity to convert
     * @return Data entity instance
     */
    @Nullable
    D toData(@Nullable T source);

    /**
     * Converts a data entity to a domain entity
     *
     * @param data Data entity to convert
     * @return Domain entity instance
     */
    @Nullable
    Collection<T> toDomain(@Nullable Collection<D> source);

    /**
     * Converts a domain entity to a data entity
     *
     * @param domain Domain entity to convert
     * @return Data entity instance
     */
    @Nullable
    Collection<D> toData(@Nullable Collection<T> source);
}
