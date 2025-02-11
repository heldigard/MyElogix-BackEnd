package com.tarapaca.api.delivery_orders.domain.model;

import com.tarapaca.api.generics.domain.model.GenericNamed;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit.MetricUnitData}
 */

@Getter
@Setter
@SuperBuilder
public class MetricUnit extends GenericNamed {
    public MetricUnit() {
        super();
    }
}
