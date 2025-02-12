package com.elogix.api.customers.domain.usecase;

import com.elogix.api.customers.domain.model.ContactPersonBasic;
import com.elogix.api.customers.domain.model.gateways.ContactPersonBasicGateway;
import com.elogix.api.generics.domain.usecase.GenericNamedBasicUseCase;

public class ContactPersonBasicUseCase
        extends GenericNamedBasicUseCase<ContactPersonBasic, ContactPersonBasicGateway> {

    public ContactPersonBasicUseCase(ContactPersonBasicGateway gateway) {
        super(gateway);
    }
}
