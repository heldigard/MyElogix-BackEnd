package com.tarapaca.api.generics.infrastructure.repository.GenericEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.tarapaca.api.generics.infrastructure.repository.GenericBasic.GenericBasicRepository;

/**
 * Extended repository interface that adds auditing capabilities
 *
 * @param <D> Entity type that extends GenericEntityData
 */
@NoRepositoryBean
public interface GenericEntityRepository<D extends GenericEntityData>
        extends GenericBasicRepository<D> {
    // Hard Delete Operation
    @Modifying
    @Transactional
    @Query("DELETE FROM #{#entityName} e WHERE e.id = :id")
    void hardDeleteById(@Param("id") Long id);
}
