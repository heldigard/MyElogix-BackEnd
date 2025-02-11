package com.tarapaca.api.customers.domain.model.gateways;

import com.tarapaca.api.customers.domain.model.EMembership;
import com.tarapaca.api.customers.domain.model.Membership;
import com.tarapaca.api.generics.domain.gateway.GenericGateway;

public interface MembershipGateway extends GenericGateway<Membership> {
    Membership deleteByName(EMembership name);

    Membership findByName(EMembership name, boolean isDeleted);
}
