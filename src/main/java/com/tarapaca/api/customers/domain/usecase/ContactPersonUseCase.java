package com.tarapaca.api.customers.domain.usecase;

import com.tarapaca.api.customers.domain.model.ContactPerson;
import com.tarapaca.api.customers.domain.model.gateways.ContactPersonGateway;
import com.tarapaca.api.generics.domain.usecase.GenericNamedUseCase;

public class ContactPersonUseCase
        extends GenericNamedUseCase<ContactPerson, ContactPersonGateway> {

    public ContactPersonUseCase(ContactPersonGateway gateway) {
        super(gateway);
    }
}
