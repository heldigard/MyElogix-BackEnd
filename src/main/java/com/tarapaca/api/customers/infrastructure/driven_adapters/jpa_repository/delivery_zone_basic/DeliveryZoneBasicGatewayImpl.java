package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic;

import com.tarapaca.api.customers.domain.model.DeliveryZoneBasic;
import com.tarapaca.api.customers.domain.model.gateways.DeliveryZoneBasicGateway;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.DeliveryZoneBasicMapper;
import com.tarapaca.api.generics.infrastructure.repository.GenericNamedBasic.GenericNamedBasicGatewayImpl;

import jakarta.persistence.EntityManager;

public class DeliveryZoneBasicGatewayImpl
        extends
        GenericNamedBasicGatewayImpl<DeliveryZoneBasic, DeliveryZoneBasicData, DeliveryZoneBasicDataJpaRepository, DeliveryZoneBasicMapper>
        implements DeliveryZoneBasicGateway {

    public DeliveryZoneBasicGatewayImpl(
            DeliveryZoneBasicDataJpaRepository repository,
            DeliveryZoneBasicMapper mapper,
            EntityManager entityManager,
            String deletedFilter) {
        super(repository, mapper, entityManager, deletedFilter);
    }
}
