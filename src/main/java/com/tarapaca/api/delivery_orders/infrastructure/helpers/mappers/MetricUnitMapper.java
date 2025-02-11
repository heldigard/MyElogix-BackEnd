package com.tarapaca.api.delivery_orders.infrastructure.helpers.mappers;

import org.springframework.stereotype.Component;

import com.tarapaca.api.delivery_orders.domain.model.MetricUnit;
import com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit.MetricUnitData;
import com.tarapaca.api.generics.infrastructure.helpers.GenericNamedMapper;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserBasicMapper;


@Component
public class MetricUnitMapper extends GenericNamedMapper<MetricUnit, MetricUnitData> {
    public MetricUnitMapper(UserBasicMapper userMapper) {
        super(MetricUnit.class, MetricUnitData.class, userMapper);
    }
}
