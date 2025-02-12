package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.gateways.CustomerGateway;
import com.elogix.api.customers.domain.usecase.BranchOfficeUseCase;
import com.elogix.api.customers.domain.usecase.CustomerUseCase;
import com.elogix.api.customers.domain.usecase.DocumentTypeUseCase;
import com.elogix.api.customers.domain.usecase.MembershipUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.CustomerMapper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class CustomerUseCaseConfig {
    @Bean
    public CustomerGatewayImpl customerGatewayImpl(
            CustomerDataJpaRepository repository,
            CustomerMapper mapper,
            DocumentTypeUseCase docTypeUseCase,
            MembershipUseCase membershipUseCase,
            BranchOfficeUseCase officeUseCase,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new CustomerGatewayImpl(
                repository,
                mapper,
                docTypeUseCase,
                membershipUseCase,
                officeUseCase,
                entityManager,
                updateUtils,
                "deletedCustomerFilter");
    }

    @Bean
    public CustomerUseCase customerUseCase(CustomerGateway gateway) {
        return new CustomerUseCase(gateway);
    }

}
