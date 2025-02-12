package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone;

import com.elogix.api.customers.domain.model.DeliveryZone;
import com.elogix.api.customers.domain.model.gateways.DeliveryZoneGateway;
import com.elogix.api.customers.infrastructure.helpers.mappers.DeliveryZoneMapper;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

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
