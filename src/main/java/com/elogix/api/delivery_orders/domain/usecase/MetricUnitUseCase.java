package com.elogix.api.delivery_orders.domain.usecase;

import com.elogix.api.delivery_orders.domain.model.MetricUnit;
import com.elogix.api.delivery_orders.domain.model.gateways.MetricUnitGateway;
import com.elogix.api.generics.domain.usecase.GenericNamedUseCase;

public class MetricUnitUseCase
        extends GenericNamedUseCase<MetricUnit, MetricUnitGateway> {
    public MetricUnitUseCase(MetricUnitGateway gateway) {
        super(gateway);
    }
}
