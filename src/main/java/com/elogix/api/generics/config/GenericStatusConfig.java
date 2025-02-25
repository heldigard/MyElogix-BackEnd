package com.elogix.api.generics.config;

import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
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

    public GenericStatusConfig(GenericConfig<T, D, R, M> config, StatusUseCase statusUseCase) {
        super(config, config.getUpdateUtils());
        this.statusUseCase = statusUseCase;
    }
}
