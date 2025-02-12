package com.elogix.api.customers.domain.usecase;

import com.elogix.api.customers.domain.model.EMembership;
import com.elogix.api.customers.domain.model.Membership;
import com.elogix.api.customers.domain.model.gateways.MembershipGateway;
import com.elogix.api.generics.domain.usecase.GenericUseCase;

public class MembershipUseCase
        extends GenericUseCase<Membership, MembershipGateway> {
    public MembershipUseCase(MembershipGateway gateway) {
        super(gateway);
    }

    public Membership deleteByName(EMembership name) {
        return gateway.deleteByName(name);
    }

    public Membership findByName(EMembership name, boolean isDeleted) {
        return gateway.findByName(name, isDeleted);
    }
}
