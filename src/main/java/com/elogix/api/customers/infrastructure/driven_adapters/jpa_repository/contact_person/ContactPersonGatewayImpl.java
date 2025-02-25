package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person;

import com.elogix.api.customers.domain.model.ContactPerson;
import com.elogix.api.customers.domain.model.gateways.ContactPersonGateway;
import com.elogix.api.customers.infrastructure.helpers.mappers.ContactPersonMapper;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;

public class ContactPersonGatewayImpl
        extends
        GenericNamedGatewayImpl<ContactPerson, ContactPersonData, ContactPersonDataJpaRepository, ContactPersonMapper>
        implements ContactPersonGateway {

    public ContactPersonGatewayImpl(
            GenericConfig<ContactPerson, ContactPersonData, ContactPersonDataJpaRepository, ContactPersonMapper> config) {
        super(config);
    }
}
