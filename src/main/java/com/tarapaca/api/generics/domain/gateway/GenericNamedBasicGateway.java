package com.tarapaca.api.generics.domain.gateway;

import java.util.List;

import com.tarapaca.api.generics.domain.model.GenericBasicEntity;

/**
 * Gateway interface that extends basic operations and adds name-based queries
 * 
 * @param <T> Type that extends GenericBasicEntity
 */
public interface GenericNamedBasicGateway<T extends GenericBasicEntity>
        extends GenericBasicGateway<T> {

    // Find by exact name
    T findByName(String name, boolean includeDeleted);

    // Find by partial name match
    List<T> findByNameContaining(String name, boolean includeDeleted);

    // Find by name pattern
    List<T> findByNameLike(String pattern, boolean includeDeleted);

    /**
     * Checks if an entity exists by name
     *
     * @param name Name to check
     * @return true if an entity with the given name exists
     */
    boolean existsByName(String name, boolean includeDeleted);
}
