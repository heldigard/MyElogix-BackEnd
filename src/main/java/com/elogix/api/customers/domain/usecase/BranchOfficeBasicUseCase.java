package com.elogix.api.customers.domain.usecase;

import com.elogix.api.customers.domain.model.BranchOfficeBasic;
import com.elogix.api.customers.domain.model.gateways.BranchOfficeBasicGateway;
import com.elogix.api.generics.domain.usecase.GenericBasicUseCase;

public class BranchOfficeBasicUseCase
        extends GenericBasicUseCase<BranchOfficeBasic, BranchOfficeBasicGateway> {
    private final BranchOfficeBasicGateway gateway;

    public BranchOfficeBasicUseCase(BranchOfficeBasicGateway gateway) {
        super(gateway);
        this.gateway = gateway;
    }

    public BranchOfficeBasic findByAddress(String address, boolean isDeleted) {
        return gateway.findByAddress(address, isDeleted);
    }
}
