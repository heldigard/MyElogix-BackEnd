package com.tarapaca.api.customers.infrastructure.helpers.mappers;

import org.springframework.stereotype.Component;

import com.tarapaca.api.customers.domain.model.DeliveryZoneBasic;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicData;
import com.tarapaca.api.generics.infrastructure.helpers.GenericNamedBasicMapper;

@Component
public class DeliveryZoneBasicMapper extends GenericNamedBasicMapper<DeliveryZoneBasic, DeliveryZoneBasicData> {

    public DeliveryZoneBasicMapper() {
        super(DeliveryZoneBasic.class, DeliveryZoneBasicData.class);
    }
}
