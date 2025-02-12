package com.elogix.api.generics.infrastructure.repository.GenericNamed;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericEntityRepository;

/**
 * Extended repository interface that combines named and audited entity
 * capabilities
 *
 * @param <D> Entity type that extends GenericEntityData with naming support
 */
@NoRepositoryBean
public interface GenericNamedRepository<D extends GenericNamedData>
        extends GenericEntityRepository<D> {
    boolean existsByName(@Param("name") String name);

    // Find by exact name match
    Optional<D> findByName(String name);

    // Find by partial name match
    List<D> findByNameContaining(String name);

    // Find by name pattern (using SQL LIKE)
    List<D> findByNameLike(String pattern);
}
