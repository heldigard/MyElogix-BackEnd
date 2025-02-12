package com.elogix.api.delivery_orders.domain.usecase;

import java.util.List;

import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.delivery_orders.domain.model.gateways.StatusGateway;
import com.elogix.api.generics.domain.usecase.GenericBasicUseCase;

public class StatusUseCase
        extends GenericBasicUseCase<Status, StatusGateway> {

    public StatusUseCase(StatusGateway gateway) {
        super(gateway);
    }

    public Status findByName(EStatus name, boolean includeDeleted) {
        return gateway.findByName(name, includeDeleted);
    }

    public List<Status> findByNameIn(List<EStatus> names, boolean includeDeleted) {
        return gateway.findByNameIn(names, includeDeleted);
    }
}
