package com.elogix.api.generics.infrastructure.repository.GenericStatus;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status.StatusData;
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

        /**
         * Retrieves only the Status object from an entity with the given ID
         *
         * @param id The ID of the entity
         * @return The Status object associated with the entity
         */
        @Query("SELECT d.status FROM #{#entityName} d WHERE d.id = :id")
        StatusData getStatus(Long id);
}
