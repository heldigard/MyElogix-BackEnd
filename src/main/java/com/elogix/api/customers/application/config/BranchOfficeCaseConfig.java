package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.BranchOffice;
import com.elogix.api.customers.domain.model.gateways.BranchOfficeGateway;
import com.elogix.api.customers.domain.usecase.BranchOfficeUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.BranchOfficeMapper;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class BranchOfficeCaseConfig {
    public static final String DELETED_FILTER = "deletedBranchOfficeFilter";

    @Bean
    public BranchOfficeGatewayImpl branchOfficeGatewayImpl(
            BranchOfficeDataJpaRepository repository,
            BranchOfficeMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {

        GenericBasicConfig<BranchOffice, BranchOfficeData, BranchOfficeDataJpaRepository, BranchOfficeMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<BranchOffice, BranchOfficeData, BranchOfficeDataJpaRepository, BranchOfficeMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new BranchOfficeGatewayImpl(genericConfig);
    }

    private GenericBasicConfig<BranchOffice, BranchOfficeData, BranchOfficeDataJpaRepository, BranchOfficeMapper> createBasicConfig(
            BranchOfficeDataJpaRepository repository,
            BranchOfficeMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public BranchOfficeUseCase branchOfficeUseCase(BranchOfficeGateway gateway) {
        return new BranchOfficeUseCase(gateway);
    }

}
