package com.tarapaca.api.customers.domain.usecase;

import com.tarapaca.api.customers.domain.model.DeliveryZone;
import com.tarapaca.api.customers.domain.model.gateways.DeliveryZoneGateway;
import com.tarapaca.api.generics.domain.usecase.GenericNamedUseCase;

public class DeliveryZoneUseCase
        extends GenericNamedUseCase<DeliveryZone, DeliveryZoneGateway> {

    public DeliveryZoneUseCase(DeliveryZoneGateway gateway) {
        super(gateway);
    }
}
