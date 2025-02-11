package com.tarapaca.api.customers.domain.model.gateways;

import com.tarapaca.api.customers.domain.model.BranchOfficeBasic;
import com.tarapaca.api.generics.domain.gateway.GenericBasicGateway;

public interface BranchOfficeBasicGateway
        extends GenericBasicGateway<BranchOfficeBasic> {
    BranchOfficeBasic findByAddress(String address, boolean isDeleted);
}
