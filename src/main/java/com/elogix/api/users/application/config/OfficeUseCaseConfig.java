package com.elogix.api.users.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.users.domain.model.Office;
import com.elogix.api.users.domain.model.gateways.OfficeGateway;
import com.elogix.api.users.domain.usecase.OfficeUseCase;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.office.OfficeData;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.office.OfficeDataJpaRepository;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.office.OfficeGatewayImpl;
import com.elogix.api.users.infrastructure.helpers.mappers.OfficeMapper;

import jakarta.persistence.EntityManager;

@Configuration
public class OfficeUseCaseConfig {
    public static final String DELETED_FILTER = "deletedOfficeFilter";

    @Bean
    public OfficeGatewayImpl officeGatewayImpl(
            OfficeDataJpaRepository repository,
            OfficeMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {

        GenericBasicConfig<Office, OfficeData, OfficeDataJpaRepository, OfficeMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<Office, OfficeData, OfficeDataJpaRepository, OfficeMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new OfficeGatewayImpl(genericConfig);
    }

    private GenericBasicConfig<Office, OfficeData, OfficeDataJpaRepository, OfficeMapper> createBasicConfig(
            OfficeDataJpaRepository repository,
            OfficeMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public OfficeUseCase officeUseCase(OfficeGateway gateway) {
        return new OfficeUseCase(gateway);
    }
}
