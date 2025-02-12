package com.elogix.api.customers.domain.usecase;

import com.elogix.api.customers.domain.model.DeliveryZoneBasic;
import com.elogix.api.customers.domain.model.gateways.DeliveryZoneBasicGateway;
import com.elogix.api.generics.domain.usecase.GenericNamedBasicUseCase;

public class DeliveryZoneBasicUseCase
        extends GenericNamedBasicUseCase<DeliveryZoneBasic, DeliveryZoneBasicGateway> {
    public DeliveryZoneBasicUseCase(DeliveryZoneBasicGateway gateway) {
        super(gateway);
    }
}
