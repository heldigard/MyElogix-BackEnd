package com.elogix.api.delivery_orders.domain.model.gateways;

import com.elogix.api.delivery_orders.domain.model.PriceList;
import com.elogix.api.generics.domain.gateway.GenericNamedGateway;

public interface PriceListGateway
        extends GenericNamedGateway<PriceList> {
    PriceList updateIsActive(Long id, boolean isActive);
}
