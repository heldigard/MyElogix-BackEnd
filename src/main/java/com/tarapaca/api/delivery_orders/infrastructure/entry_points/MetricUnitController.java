package com.tarapaca.api.delivery_orders.infrastructure.entry_points;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.delivery_orders.domain.model.MetricUnit;
import com.tarapaca.api.delivery_orders.domain.model.gateways.MetricUnitGateway;
import com.tarapaca.api.delivery_orders.domain.usecase.MetricUnitUseCase;
import com.tarapaca.api.generics.infrastructure.entry_points.GenericNamedController;

@RestController
@RequestMapping("/api/v1/metric-unit")
public class MetricUnitController
        extends GenericNamedController<MetricUnit, MetricUnitGateway, MetricUnitUseCase> {

    public MetricUnitController(MetricUnitUseCase useCase) {
        super(useCase);
    }
}
