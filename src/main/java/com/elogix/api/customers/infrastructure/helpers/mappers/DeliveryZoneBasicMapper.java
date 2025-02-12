package com.elogix.api.customers.infrastructure.helpers.mappers;

import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.DeliveryZoneBasic;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicData;
import com.elogix.api.generics.infrastructure.helpers.GenericNamedBasicMapper;

@Component
public class DeliveryZoneBasicMapper extends GenericNamedBasicMapper<DeliveryZoneBasic, DeliveryZoneBasicData> {

    public DeliveryZoneBasicMapper() {
        super(DeliveryZoneBasic.class, DeliveryZoneBasicData.class);
    }
}
