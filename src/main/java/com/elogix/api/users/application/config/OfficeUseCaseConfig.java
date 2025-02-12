package com.elogix.api.users.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.users.domain.model.gateways.OfficeGateway;
import com.elogix.api.users.domain.usecase.OfficeUseCase;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.office.OfficeDataJpaRepository;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.office.OfficeGatewayImpl;
import com.elogix.api.users.infrastructure.helpers.mappers.OfficeMapper;

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
