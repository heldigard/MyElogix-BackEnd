package com.elogix.api.customers.domain.usecase;

import com.elogix.api.customers.domain.model.Customer;
import com.elogix.api.customers.domain.model.gateways.CustomerGateway;
import com.elogix.api.generics.domain.usecase.GenericNamedUseCase;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import com.elogix.api.shared.domain.model.Hits;

public class CustomerUseCase
        extends GenericNamedUseCase<Customer, CustomerGateway> {
    private final CustomerGateway gateway;
    // private static final Logger logger = LoggerFactory.getLogger(CustomerUseCase.class);

    public CustomerUseCase(CustomerGateway gateway) {
        super(gateway);
        this.gateway = gateway;
    }

    public void deleteByDocumentNumber(String documentNumber) {
        gateway.deleteByDocumentNumber(documentNumber);
    }

    public Customer findByEmail(String email, boolean isDeleted) {
        return gateway.findByEmail(email, isDeleted);
    }

    public Customer findByDocumentNumber(String documentNumber, boolean isDeleted) {
        return gateway.findByDocumentNumber(documentNumber, isDeleted);
    }

    public int updateHits(Hits hits) {
        return gateway.updateHits(hits);
    }

    public int incrementHits(Hits hits) {
        return gateway.incrementHits(hits);
    }

    public void incrementOneHit(Long id) {
        gateway.incrementOneHit(id);
    }
}
