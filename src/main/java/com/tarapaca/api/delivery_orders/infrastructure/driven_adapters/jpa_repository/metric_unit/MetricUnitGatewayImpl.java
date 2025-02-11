package com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit;

import com.tarapaca.api.delivery_orders.domain.model.MetricUnit;
import com.tarapaca.api.delivery_orders.domain.model.gateways.MetricUnitGateway;
import com.tarapaca.api.delivery_orders.infrastructure.helpers.mappers.MetricUnitMapper;
import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

public class MetricUnitGatewayImpl
        extends GenericNamedGatewayImpl<MetricUnit, MetricUnitData, MetricUnitDataJpaRepository, MetricUnitMapper>
        implements MetricUnitGateway {

    public MetricUnitGatewayImpl(
            MetricUnitDataJpaRepository repository,
            MetricUnitMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
    }
}
