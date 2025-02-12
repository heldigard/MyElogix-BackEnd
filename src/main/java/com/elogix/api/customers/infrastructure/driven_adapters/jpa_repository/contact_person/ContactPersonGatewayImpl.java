package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person;

import com.elogix.api.customers.domain.model.ContactPerson;
import com.elogix.api.customers.domain.model.gateways.ContactPersonGateway;
import com.elogix.api.customers.infrastructure.helpers.mappers.ContactPersonMapper;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

public class ContactPersonGatewayImpl
        extends
        GenericNamedGatewayImpl<ContactPerson, ContactPersonData, ContactPersonDataJpaRepository, ContactPersonMapper>
        implements ContactPersonGateway {

    public ContactPersonGatewayImpl(
            ContactPersonDataJpaRepository repository,
            ContactPersonMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
    }
}
