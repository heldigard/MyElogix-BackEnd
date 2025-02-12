package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.gateways.CustomerBasicGateway;
import com.elogix.api.customers.domain.usecase.CustomerBasicUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer_basic.CustomerBasicDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer_basic.CustomerBasicGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.CustomerBasicMapper;

import jakarta.persistence.EntityManager;

@Configuration
public class CustomerBasicUseCaseConfig {
    @Bean
    public CustomerBasicGatewayImpl customerBasicGatewayImpl(
            CustomerBasicDataJpaRepository repository,
            CustomerBasicMapper mapper,
            EntityManager entityManager
    ) {
        return new CustomerBasicGatewayImpl(
                repository,
                mapper,
                entityManager,
                "deletedCustomerBasicFilter"
        );
    }

    @Bean
    public CustomerBasicUseCase customerBasicUseCase(CustomerBasicGateway gateway) {
        return new CustomerBasicUseCase(gateway);
    }

}
