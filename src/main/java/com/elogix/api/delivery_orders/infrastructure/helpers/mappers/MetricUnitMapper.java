package com.elogix.api.delivery_orders.infrastructure.helpers.mappers;

import org.springframework.stereotype.Component;

import com.elogix.api.delivery_orders.domain.model.MetricUnit;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit.MetricUnitData;
import com.elogix.api.generics.infrastructure.helpers.GenericNamedMapper;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;


@Component
public class MetricUnitMapper extends GenericNamedMapper<MetricUnit, MetricUnitData> {
    public MetricUnitMapper(UserBasicMapper userMapper) {
        super(MetricUnit.class, MetricUnitData.class, userMapper);
    }
}
