package com.elogix.api.customers.domain.usecase;

import com.elogix.api.customers.domain.model.BranchOffice;
import com.elogix.api.customers.domain.model.gateways.BranchOfficeGateway;
import com.elogix.api.generics.domain.usecase.GenericUseCase;

public class BranchOfficeUseCase
        extends GenericUseCase<BranchOffice, BranchOfficeGateway> {
    private final BranchOfficeGateway gateway;

    public BranchOfficeUseCase(BranchOfficeGateway gateway) {
        super(gateway);
        this.gateway = gateway;
    }

    public BranchOffice findByAddress(String address, boolean isDeleted) {
        return gateway.findByAddress(address, isDeleted);
    }
}
