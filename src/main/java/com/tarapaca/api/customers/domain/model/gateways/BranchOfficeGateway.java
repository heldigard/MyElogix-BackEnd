package com.tarapaca.api.customers.domain.model.gateways;

import com.tarapaca.api.customers.domain.model.BranchOffice;
import com.tarapaca.api.generics.domain.gateway.GenericGateway;

public interface BranchOfficeGateway
        extends GenericGateway<BranchOffice> {
    BranchOffice findByAddress(String address, boolean isDeleted);
}
