package com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit;

import com.elogix.api.delivery_orders.domain.model.MetricUnit;
import com.elogix.api.delivery_orders.domain.model.gateways.MetricUnitGateway;
import com.elogix.api.delivery_orders.infrastructure.helpers.mappers.MetricUnitMapper;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;

public class MetricUnitGatewayImpl
        extends GenericNamedGatewayImpl<MetricUnit, MetricUnitData, MetricUnitDataJpaRepository, MetricUnitMapper>
        implements MetricUnitGateway {

    public MetricUnitGatewayImpl(
            GenericConfig<MetricUnit, MetricUnitData, MetricUnitDataJpaRepository, MetricUnitMapper> config) {
        super(config);
    }
}
