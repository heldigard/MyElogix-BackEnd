package com.elogix.api.generics.config;

import com.elogix.api.generics.domain.model.GenericEntity;
import com.elogix.api.generics.infrastructure.helpers.GenericMapperGateway;
import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericEntityData;
import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericEntityRepository;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(builderMethodName = "genericBuilder", toBuilder = true)
public class GenericConfig<T extends GenericEntity, D extends GenericEntityData, R extends GenericEntityRepository<D>, M extends GenericMapperGateway<T, D>>
        extends GenericBasicConfig<T, D, R, M> {
    private final UpdateUtils updateUtils;

    public GenericConfig(GenericBasicConfig<T, D, R, M> config, UpdateUtils updateUtils) {
        super(config.getRepository(), config.getMapper(), config.getEntityManager(), config.getDeletedFilter());
        this.updateUtils = updateUtils;
    }
}
