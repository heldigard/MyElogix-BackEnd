package com.elogix.api.generics.infrastructure.repository.GenericStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.generics.config.GenericStatusConfig;
import com.elogix.api.generics.domain.gateway.GenericStatusGateway;
import com.elogix.api.generics.domain.model.GenericStatus;
import com.elogix.api.generics.infrastructure.helpers.GenericMapperGateway;
import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericGatewayImpl;

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
            GenericStatusConfig<T, D, R, M> config) {
        super(config);
        this.statusUseCase = config.getStatusUseCase();
    }

    @Override
    public void preserveExistingData(T newEntity, T existing) {
        super.preserveExistingData(newEntity, existing);

        if (newEntity.getStatus() == null)
            newEntity.setStatus(existing.getStatus());
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
