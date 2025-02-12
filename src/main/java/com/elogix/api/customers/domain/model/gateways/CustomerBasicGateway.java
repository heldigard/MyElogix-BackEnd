package com.elogix.api.customers.domain.model.gateways;

import com.elogix.api.customers.domain.model.CustomerBasic;
import com.elogix.api.generics.domain.gateway.GenericNamedBasicGateway;

public interface CustomerBasicGateway
        extends GenericNamedBasicGateway<CustomerBasic> {
    CustomerBasic findByEmail(String email, boolean isDeleted);

    CustomerBasic findByDocumentNumber(String documentNumber, boolean isDeleted);
}
