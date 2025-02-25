package com.elogix.api.users.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.users.domain.model.RoleModel;
import com.elogix.api.users.domain.model.gateways.RoleGateway;
import com.elogix.api.users.domain.usecase.RoleUseCase;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.role.RoleData;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.role.RoleDataJpaRepository;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.role.RoleGatewayImpl;
import com.elogix.api.users.infrastructure.helpers.mappers.RoleMapper;

import jakarta.persistence.EntityManager;

@Configuration
public class RoleUseCaseConfig {
    public static final String DELETED_FILTER = "deletedRoleFilter";

    @Bean
    public RoleGatewayImpl roleGatewayImpl(
            RoleDataJpaRepository repository,
            RoleMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {

        GenericBasicConfig<RoleModel, RoleData, RoleDataJpaRepository, RoleMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<RoleModel, RoleData, RoleDataJpaRepository, RoleMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new RoleGatewayImpl(genericConfig);
    }

    private GenericBasicConfig<RoleModel, RoleData, RoleDataJpaRepository, RoleMapper> createBasicConfig(
            RoleDataJpaRepository repository,
            RoleMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public RoleUseCase roleUseCase(RoleGateway gateway) {
        return new RoleUseCase(gateway);
    }
}
