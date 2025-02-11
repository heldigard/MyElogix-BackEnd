package com.tarapaca.api.users.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;
import com.tarapaca.api.users.domain.model.gateways.OfficeGateway;
import com.tarapaca.api.users.domain.usecase.OfficeUseCase;
import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.office.OfficeDataJpaRepository;
import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.office.OfficeGatewayImpl;
import com.tarapaca.api.users.infrastructure.helpers.mappers.OfficeMapper;

import jakarta.persistence.EntityManager;

@Configuration
public class OfficeUseCaseConfig {
    @Bean
    public OfficeGatewayImpl officeGatewayImpl(
            OfficeDataJpaRepository repository,
            OfficeMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new OfficeGatewayImpl(repository, mapper, entityManager, updateUtils, "deletedOfficeFilter");
    }

    @Bean
    public OfficeUseCase officeUseCase(OfficeGateway gateway) {
        return new OfficeUseCase(gateway);
    }
}
