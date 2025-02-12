package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.gateways.BranchOfficeGateway;
import com.elogix.api.customers.domain.usecase.BranchOfficeUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeGatewayImpl;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeJpaRepository;
import com.elogix.api.customers.infrastructure.helpers.mappers.BranchOfficeMapper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

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
