package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic;

import com.elogix.api.customers.domain.model.DeliveryZoneBasic;
import com.elogix.api.customers.domain.model.gateways.DeliveryZoneBasicGateway;
import com.elogix.api.customers.infrastructure.helpers.mappers.DeliveryZoneBasicMapper;
import com.elogix.api.generics.infrastructure.repository.GenericNamedBasic.GenericNamedBasicGatewayImpl;

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
