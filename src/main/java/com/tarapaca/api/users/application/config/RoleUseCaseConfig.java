package com.tarapaca.api.users.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;
import com.tarapaca.api.users.domain.model.gateways.RoleGateway;
import com.tarapaca.api.users.domain.usecase.RoleUseCase;
import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.role.RoleDataJpaRepository;
import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.role.RoleGatewayImpl;
import com.tarapaca.api.users.infrastructure.helpers.mappers.RoleMapper;

import jakarta.persistence.EntityManager;

@Configuration
public class RoleUseCaseConfig {
    @Bean
    RoleGatewayImpl roleGatewayImpl(
            RoleMapper mapper,
            RoleDataJpaRepository repository,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new RoleGatewayImpl(repository, mapper, entityManager, updateUtils, "deletedRoleFilter");
    }

    @Bean
    public RoleUseCase roleUseCase(RoleGateway gateway) {
        return new RoleUseCase(gateway);
    }
}
