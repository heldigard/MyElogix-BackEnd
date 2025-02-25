package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.Customer;
import com.elogix.api.customers.domain.model.gateways.CustomerGateway;
import com.elogix.api.customers.domain.usecase.BranchOfficeUseCase;
import com.elogix.api.customers.domain.usecase.CustomerUseCase;
import com.elogix.api.customers.domain.usecase.DocumentTypeUseCase;
import com.elogix.api.customers.domain.usecase.MembershipUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.CustomerMapper;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class CustomerUseCaseConfig {
    public static final String DELETED_FILTER = "deletedCustomerFilter";

    @Bean
    public CustomerGatewayImpl customerGatewayImpl(
            CustomerDataJpaRepository repository,
            CustomerMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            DocumentTypeUseCase docTypeUseCase,
            MembershipUseCase membershipUseCase,
            BranchOfficeUseCase officeUseCase) {

        GenericBasicConfig<Customer, CustomerData, CustomerDataJpaRepository, CustomerMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<Customer, CustomerData, CustomerDataJpaRepository, CustomerMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new CustomerGatewayImpl(
                genericConfig,
                docTypeUseCase,
                membershipUseCase,
                officeUseCase);
    }

    private GenericBasicConfig<Customer, CustomerData, CustomerDataJpaRepository, CustomerMapper> createBasicConfig(
            CustomerDataJpaRepository repository,
            CustomerMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public CustomerUseCase customerUseCase(CustomerGateway gateway) {
        return new CustomerUseCase(gateway);
    }
}
