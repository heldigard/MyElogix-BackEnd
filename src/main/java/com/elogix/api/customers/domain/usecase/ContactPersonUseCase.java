package com.elogix.api.customers.domain.usecase;

import com.elogix.api.customers.domain.model.ContactPerson;
import com.elogix.api.customers.domain.model.gateways.ContactPersonGateway;
import com.elogix.api.generics.domain.usecase.GenericNamedUseCase;

public class ContactPersonUseCase
        extends GenericNamedUseCase<ContactPerson, ContactPersonGateway> {

    public ContactPersonUseCase(ContactPersonGateway gateway) {
        super(gateway);
    }
}
