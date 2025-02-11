package com.tarapaca.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.customers.domain.model.gateways.BranchOfficeGateway;
import com.tarapaca.api.customers.domain.usecase.BranchOfficeUseCase;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeGatewayImpl;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeJpaRepository;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.BranchOfficeMapper;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class BranchOfficeCaseConfig {
    @Bean
    public BranchOfficeGatewayImpl branchOfficeGatewayImpl(
            BranchOfficeJpaRepository repository,
            BranchOfficeMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new BranchOfficeGatewayImpl(
                repository,
                mapper,
                entityManager,
                updateUtils,
                "deletedBranchOfficeFilter");
    }

    @Bean
    public BranchOfficeUseCase branchOfficeUseCase(BranchOfficeGateway gateway) {
        return new BranchOfficeUseCase(gateway);
    }

}
