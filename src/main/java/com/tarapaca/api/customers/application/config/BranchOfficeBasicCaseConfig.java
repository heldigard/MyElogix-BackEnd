package com.tarapaca.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.customers.domain.model.gateways.BranchOfficeBasicGateway;
import com.tarapaca.api.customers.domain.usecase.BranchOfficeBasicUseCase;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office_basic.BranchOfficeBasicDataJpaRepository;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office_basic.BranchOfficeBasicGatewayImpl;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.BranchOfficeBasicMapper;

import jakarta.persistence.EntityManager;

@Configuration
public class BranchOfficeBasicCaseConfig {
    @Bean
    public BranchOfficeBasicGatewayImpl branchOfficeBasicGatewayImpl(
            BranchOfficeBasicDataJpaRepository repository,
            BranchOfficeBasicMapper mapper,
            EntityManager entityManager
    ) {
        return new BranchOfficeBasicGatewayImpl(
                repository,
                mapper,
                entityManager,
                "deletedBranchOfficeBasicFilter"
        );
    }

    @Bean
    public BranchOfficeBasicUseCase branchOfficeBasicUseCase(BranchOfficeBasicGateway gateway) {
        return new BranchOfficeBasicUseCase(gateway);
    }

}
