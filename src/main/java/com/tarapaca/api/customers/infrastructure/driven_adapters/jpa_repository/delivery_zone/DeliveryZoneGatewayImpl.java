package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone;

import com.tarapaca.api.customers.domain.model.DeliveryZone;
import com.tarapaca.api.customers.domain.model.gateways.DeliveryZoneGateway;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.DeliveryZoneMapper;
import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

public class DeliveryZoneGatewayImpl
        extends
        GenericNamedGatewayImpl<DeliveryZone, DeliveryZoneData, DeliveryZoneDataJpaRepository, DeliveryZoneMapper>
        implements DeliveryZoneGateway {

    public DeliveryZoneGatewayImpl(
            DeliveryZoneDataJpaRepository repository,
            DeliveryZoneMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
    }
}
