package com.tarapaca.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.customers.domain.model.gateways.ContactPersonGateway;
import com.tarapaca.api.customers.domain.usecase.ContactPersonUseCase;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person.ContactPersonDataJpaRepository;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person.ContactPersonGatewayImpl;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.ContactPersonMapper;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class ContactPersonUseCaseConfig {
    @Bean
    public ContactPersonGatewayImpl contactPersonGatewayImpl(
            ContactPersonDataJpaRepository repository,
            ContactPersonMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new ContactPersonGatewayImpl(
                repository,
                mapper,
                entityManager,
                updateUtils,
                "deletedContactPersonFilter");
    }

    @Bean
    public ContactPersonUseCase contactPersonUseCase(ContactPersonGateway gateway) {
        return new ContactPersonUseCase(gateway);
    }

}
