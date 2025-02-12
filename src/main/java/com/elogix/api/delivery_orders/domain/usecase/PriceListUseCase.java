package com.elogix.api.delivery_orders.domain.usecase;

import com.elogix.api.delivery_orders.domain.model.PriceList;
import com.elogix.api.delivery_orders.domain.model.gateways.PriceListGateway;
import com.elogix.api.generics.domain.usecase.GenericNamedUseCase;

public class PriceListUseCase
        extends GenericNamedUseCase<PriceList, PriceListGateway> {

    public PriceListUseCase(PriceListGateway gateway) {
        super(gateway);
    }

    public PriceList updateIsActive(Long id, boolean isActive) {
        return gateway.updateIsActive(id, isActive);
    }
}
