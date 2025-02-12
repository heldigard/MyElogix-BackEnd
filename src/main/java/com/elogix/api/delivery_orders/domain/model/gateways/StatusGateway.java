package com.elogix.api.delivery_orders.domain.model.gateways;

import java.util.List;

import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.generics.domain.gateway.GenericBasicGateway;

public interface StatusGateway
        extends GenericBasicGateway<Status> {
    Status findByName(EStatus name, boolean includeDeleted);

    List<Status> findByNameIn(List<EStatus> names, boolean includeDeleted);
}
