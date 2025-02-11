package com.tarapaca.api.customers.domain.usecase;

import com.tarapaca.api.customers.domain.model.BranchOfficeBasic;
import com.tarapaca.api.customers.domain.model.gateways.BranchOfficeBasicGateway;
import com.tarapaca.api.generics.domain.usecase.GenericBasicUseCase;

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
