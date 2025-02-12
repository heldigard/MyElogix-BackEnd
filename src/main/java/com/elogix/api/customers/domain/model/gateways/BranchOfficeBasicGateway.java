package com.elogix.api.customers.domain.model.gateways;

import com.elogix.api.customers.domain.model.BranchOfficeBasic;
import com.elogix.api.generics.domain.gateway.GenericBasicGateway;

public interface BranchOfficeBasicGateway
        extends GenericBasicGateway<BranchOfficeBasic> {
    BranchOfficeBasic findByAddress(String address, boolean isDeleted);
}
