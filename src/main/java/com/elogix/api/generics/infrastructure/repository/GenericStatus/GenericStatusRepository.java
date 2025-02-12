package com.elogix.api.generics.infrastructure.repository.GenericStatus;

import org.springframework.data.repository.NoRepositoryBean;

import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericEntityRepository;

/**
 * Generic repository interface for production-related entities
 * Extends basic entity repository functionality with production-specific
 * operations
 *
 * @param <D> Entity type that extends GenericProductionData
 */
@NoRepositoryBean
public interface GenericStatusRepository<D extends GenericStatusData>
        extends GenericEntityRepository<D> {
}
