package com.tarapaca.api.delivery_orders.domain.usecase;

import com.tarapaca.api.delivery_orders.domain.model.MetricUnit;
import com.tarapaca.api.delivery_orders.domain.model.gateways.MetricUnitGateway;
import com.tarapaca.api.generics.domain.usecase.GenericNamedUseCase;

public class MetricUnitUseCase
        extends GenericNamedUseCase<MetricUnit, MetricUnitGateway> {
    public MetricUnitUseCase(MetricUnitGateway gateway) {
        super(gateway);
    }
}
