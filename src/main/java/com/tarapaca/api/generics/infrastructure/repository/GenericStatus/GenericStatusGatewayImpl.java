package com.tarapaca.api.generics.infrastructure.repository.GenericStatus;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tarapaca.api.delivery_orders.domain.model.EStatus;
import com.tarapaca.api.delivery_orders.domain.model.Status;
import com.tarapaca.api.delivery_orders.domain.usecase.StatusUseCase;
import com.tarapaca.api.generics.domain.gateway.GenericStatusGateway;
import com.tarapaca.api.generics.domain.model.GenericStatus;
import com.tarapaca.api.generics.infrastructure.helpers.GenericMapperGateway;
import com.tarapaca.api.generics.infrastructure.repository.GenericEntity.GenericGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Generic implementation for production-related gateway operations.
 *
 * @param <T> The domain entity type extending GenericProduction
 * @param <D> The data entity type extending GenericProductionData
 * @param <R> The repository type extending both JpaRepository and
 *            GenericProductionRepository
 * @param <M> The mapper type extending GenericMapperGateway
 */
@Slf4j
public abstract class GenericStatusGatewayImpl<T extends GenericStatus, D extends GenericStatusData, R extends JpaRepository<D, Long> & GenericStatusRepository<D>, M extends GenericMapperGateway<T, D>>
        extends GenericGatewayImpl<T, D, R, M>
        implements GenericStatusGateway<T> {

    protected final StatusUseCase statusUseCase;

    /**
     * Constructor for GenericProductionGatewayImpl
     */
    protected GenericStatusGatewayImpl(
            R repository,
            M mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            StatusUseCase statusUseCase,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
        this.statusUseCase = statusUseCase;
        this.deletedFilter = deletedFilter;
    }

    @Override
    @Transactional
    public T updateStatus(Long id, Status status) {
        T entity = findById(id, false);
        entity.setStatus(status);
        return save(entity);
    }

    @Override
    @Transactional
    public T updateStatus(Long id, EStatus statusName) {
        Status status = statusUseCase.findByName(statusName, false);
        return updateStatus(id, status);
    }

    @Override
    @Transactional
    public T updateStatus(Long id, String status) {
        // Convert string to enum
        EStatus statusEnum = EStatus.valueOf(status.toUpperCase());

        // Reuse existing method that handles EStatus
        return updateStatus(id, statusEnum);
    }
}
