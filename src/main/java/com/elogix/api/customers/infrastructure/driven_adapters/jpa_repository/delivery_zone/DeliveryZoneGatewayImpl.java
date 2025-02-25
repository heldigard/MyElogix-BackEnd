package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone;

import com.elogix.api.customers.domain.model.DeliveryZone;
import com.elogix.api.customers.domain.model.gateways.DeliveryZoneGateway;
import com.elogix.api.customers.infrastructure.helpers.mappers.DeliveryZoneMapper;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;

public class DeliveryZoneGatewayImpl
        extends
        GenericNamedGatewayImpl<DeliveryZone, DeliveryZoneData, DeliveryZoneDataJpaRepository, DeliveryZoneMapper>
        implements DeliveryZoneGateway {

    public DeliveryZoneGatewayImpl(
            GenericConfig<DeliveryZone, DeliveryZoneData, DeliveryZoneDataJpaRepository, DeliveryZoneMapper> config) {
        super(config);
    }
}
