package com.tarapaca.api.delivery_orders.domain.model.gateways;

import com.tarapaca.api.delivery_orders.domain.model.PriceList;
import com.tarapaca.api.generics.domain.gateway.GenericNamedGateway;

public interface PriceListGateway
        extends GenericNamedGateway<PriceList> {
    PriceList updateIsActive(Long id, boolean isActive);
}
