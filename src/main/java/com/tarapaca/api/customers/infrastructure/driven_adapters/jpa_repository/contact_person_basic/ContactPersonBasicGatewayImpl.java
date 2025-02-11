package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person_basic;

import com.tarapaca.api.customers.domain.model.ContactPersonBasic;
import com.tarapaca.api.customers.domain.model.gateways.ContactPersonBasicGateway;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.ContactPersonBasicMapper;
import com.tarapaca.api.generics.infrastructure.repository.GenericNamedBasic.GenericNamedBasicGatewayImpl;

import jakarta.persistence.EntityManager;

public class ContactPersonBasicGatewayImpl
        extends
        GenericNamedBasicGatewayImpl<ContactPersonBasic, ContactPersonBasicData, ContactPersonBasicDataJpaRepository, ContactPersonBasicMapper>
        implements ContactPersonBasicGateway {

    public ContactPersonBasicGatewayImpl(
            ContactPersonBasicDataJpaRepository repository,
            ContactPersonBasicMapper mapper,
            EntityManager entityManager,
            String deletedFilter) {
        super(repository, mapper, entityManager, deletedFilter);
    }
}
