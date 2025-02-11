package com.tarapaca.api.generics.infrastructure.repository.GenericNamedBasic;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;

import com.tarapaca.api.generics.infrastructure.repository.GenericBasic.GenericBasicRepository;

/**
 * Repository interface that adds name-based queries
 *
 * @param <D> Entity type that extends GenericBasicEntityData
 */
@NoRepositoryBean
public interface GenericNamedBasicRepository<D extends GenericNamedBasicData>
        extends GenericBasicRepository<D> {

    // Find by exact name match
    Optional<D> findByName(String name);

    // Find by partial name match
    List<D> findByNameContaining(String name);

    // Find by name pattern (using SQL LIKE)
    List<D> findByNameLike(String pattern);

    // Check if name exists
    boolean existsByName(String name);
}
