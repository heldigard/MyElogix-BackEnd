package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.ContactPerson;
import com.elogix.api.customers.domain.model.gateways.ContactPersonGateway;
import com.elogix.api.customers.domain.usecase.ContactPersonUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person.ContactPersonData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person.ContactPersonDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person.ContactPersonGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.ContactPersonMapper;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class ContactPersonUseCaseConfig {
    public static final String DELETED_FILTER = "deletedContactPersonFilter";

    @Bean
    public ContactPersonGatewayImpl contactPersonGatewayImpl(
            ContactPersonDataJpaRepository repository,
            ContactPersonMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {

        GenericBasicConfig<ContactPerson, ContactPersonData, ContactPersonDataJpaRepository, ContactPersonMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<ContactPerson, ContactPersonData, ContactPersonDataJpaRepository, ContactPersonMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new ContactPersonGatewayImpl(genericConfig);
    }

    private GenericBasicConfig<ContactPerson, ContactPersonData, ContactPersonDataJpaRepository, ContactPersonMapper> createBasicConfig(
            ContactPersonDataJpaRepository repository,
            ContactPersonMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public ContactPersonUseCase contactPersonUseCase(ContactPersonGateway gateway) {
        return new ContactPersonUseCase(gateway);
    }

}
