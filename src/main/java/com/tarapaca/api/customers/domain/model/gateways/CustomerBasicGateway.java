package com.tarapaca.api.customers.domain.model.gateways;

import com.tarapaca.api.customers.domain.model.CustomerBasic;
import com.tarapaca.api.generics.domain.gateway.GenericNamedBasicGateway;

public interface CustomerBasicGateway
        extends GenericNamedBasicGateway<CustomerBasic> {
    CustomerBasic findByEmail(String email, boolean isDeleted);

    CustomerBasic findByDocumentNumber(String documentNumber, boolean isDeleted);
}
