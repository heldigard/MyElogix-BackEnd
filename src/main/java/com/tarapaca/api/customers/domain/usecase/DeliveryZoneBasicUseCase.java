package com.tarapaca.api.customers.domain.usecase;

import com.tarapaca.api.customers.domain.model.DeliveryZoneBasic;
import com.tarapaca.api.customers.domain.model.gateways.DeliveryZoneBasicGateway;
import com.tarapaca.api.generics.domain.usecase.GenericNamedBasicUseCase;

public class DeliveryZoneBasicUseCase
        extends GenericNamedBasicUseCase<DeliveryZoneBasic, DeliveryZoneBasicGateway> {
    public DeliveryZoneBasicUseCase(DeliveryZoneBasicGateway gateway) {
        super(gateway);
    }
}
