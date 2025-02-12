package com.elogix.api.customers.domain.usecase;

import com.elogix.api.customers.domain.model.DeliveryZone;
import com.elogix.api.customers.domain.model.gateways.DeliveryZoneGateway;
import com.elogix.api.generics.domain.usecase.GenericNamedUseCase;

public class DeliveryZoneUseCase
        extends GenericNamedUseCase<DeliveryZone, DeliveryZoneGateway> {

    public DeliveryZoneUseCase(DeliveryZoneGateway gateway) {
        super(gateway);
    }
}
