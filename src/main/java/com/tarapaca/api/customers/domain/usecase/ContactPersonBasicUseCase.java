package com.tarapaca.api.customers.domain.usecase;

import com.tarapaca.api.customers.domain.model.ContactPersonBasic;
import com.tarapaca.api.customers.domain.model.gateways.ContactPersonBasicGateway;
import com.tarapaca.api.generics.domain.usecase.GenericNamedBasicUseCase;

public class ContactPersonBasicUseCase
        extends GenericNamedBasicUseCase<ContactPersonBasic, ContactPersonBasicGateway> {

    public ContactPersonBasicUseCase(ContactPersonBasicGateway gateway) {
        super(gateway);
    }
}
