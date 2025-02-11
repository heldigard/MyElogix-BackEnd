package com.tarapaca.api.customers.domain.usecase;

import com.tarapaca.api.customers.domain.model.CustomerBasic;
import com.tarapaca.api.customers.domain.model.gateways.CustomerBasicGateway;
import com.tarapaca.api.generics.domain.usecase.GenericNamedBasicUseCase;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

public class CustomerBasicUseCase
        extends GenericNamedBasicUseCase<CustomerBasic, CustomerBasicGateway> {
    private final CustomerBasicGateway gateway;
    // private static final Logger logger = LoggerFactory.getLogger(CustomerBasicUseCase.class);

    public CustomerBasicUseCase(CustomerBasicGateway gateway) {
        super(gateway);
        this.gateway = gateway;
    }

    public CustomerBasic findByEmail(String email, boolean isDeleted) {
        return gateway.findByEmail(email, isDeleted);
    }

    public CustomerBasic findByDocumentNumber(String documentNumber, boolean isDeleted) {
        return gateway.findByDocumentNumber(documentNumber, isDeleted);
    }
}
