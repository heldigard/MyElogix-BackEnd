package com.elogix.api.customers.domain.model.gateways;

import com.elogix.api.customers.domain.model.Customer;
import com.elogix.api.generics.domain.gateway.GenericNamedGateway;
import com.elogix.api.shared.domain.model.Hits;

public interface CustomerGateway
        extends GenericNamedGateway<Customer> {
    void deleteByDocumentNumber(String documentNumber);

    Customer findByEmail(String email, boolean isDeleted);

    Customer findByDocumentNumber(String documentNumber, boolean isDeleted);

    int updateHits(Hits hits);

    int incrementHits(Hits hits);

    void incrementOneHit(Long id);
}
