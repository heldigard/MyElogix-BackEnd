package com.elogix.api.generics.config;

import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.delivery_orders.infrastructure.helpers.mappers.StatusMapper;
import com.elogix.api.generics.domain.model.GenericStatus;
import com.elogix.api.generics.infrastructure.helpers.GenericMapperGateway;
import com.elogix.api.generics.infrastructure.repository.GenericStatus.GenericStatusData;
import com.elogix.api.generics.infrastructure.repository.GenericStatus.GenericStatusRepository;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(builderMethodName = "statusBuilder", toBuilder = true)
public class GenericStatusConfig<T extends GenericStatus, D extends GenericStatusData, R extends GenericStatusRepository<D>, M extends GenericMapperGateway<T, D>>
        extends GenericConfig<T, D, R, M> {
    protected final StatusUseCase statusUseCase;
    protected final StatusMapper statusMapper;

    public GenericStatusConfig(
            GenericConfig<T, D, R, M> config,
            StatusUseCase statusUseCase,
            StatusMapper statusMapper) {
        super(config, config.getUpdateUtils());
        this.statusUseCase = statusUseCase;
        this.statusMapper = statusMapper;
    }
}
