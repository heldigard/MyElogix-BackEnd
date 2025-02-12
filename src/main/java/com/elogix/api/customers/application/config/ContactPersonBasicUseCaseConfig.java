package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.gateways.ContactPersonBasicGateway;
import com.elogix.api.customers.domain.usecase.ContactPersonBasicUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person_basic.ContactPersonBasicDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person_basic.ContactPersonBasicGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.ContactPersonBasicMapper;

import jakarta.persistence.EntityManager;

@Configuration
public class ContactPersonBasicUseCaseConfig {
    @Bean
    public ContactPersonBasicGatewayImpl contactPersonBasicGatewayImpl(
            ContactPersonBasicDataJpaRepository repository,
            ContactPersonBasicMapper mapper,
            EntityManager entityManager
    ) {
        return new ContactPersonBasicGatewayImpl(
                repository,
                mapper,
                entityManager,
                "deletedContactPersonBasicFilter"
        );
    }

    @Bean
    public ContactPersonBasicUseCase contactPersonBasicUseCase(ContactPersonBasicGateway gateway) {
        return new ContactPersonBasicUseCase(gateway);
    }

}
