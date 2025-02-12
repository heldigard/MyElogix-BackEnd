package com.elogix.api.customers.domain.model.gateways;

import com.elogix.api.customers.domain.model.BranchOffice;
import com.elogix.api.generics.domain.gateway.GenericGateway;

public interface BranchOfficeGateway
        extends GenericGateway<BranchOffice> {
    BranchOffice findByAddress(String address, boolean isDeleted);
}
