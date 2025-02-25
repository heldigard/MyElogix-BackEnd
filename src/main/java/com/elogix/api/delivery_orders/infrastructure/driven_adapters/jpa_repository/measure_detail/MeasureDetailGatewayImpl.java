package com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.measure_detail;

import com.elogix.api.delivery_orders.domain.model.MeasureDetail;
import com.elogix.api.delivery_orders.domain.model.gateways.MeasureDetailGateway;
import com.elogix.api.delivery_orders.infrastructure.helpers.mappers.MeasureDetailMapper;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;

public class MeasureDetailGatewayImpl
        extends
        GenericNamedGatewayImpl<MeasureDetail, MeasureDetailData, MeasureDetailDataJpaRepository, MeasureDetailMapper>
        implements MeasureDetailGateway {

    public MeasureDetailGatewayImpl(
            GenericConfig<MeasureDetail, MeasureDetailData, MeasureDetailDataJpaRepository, MeasureDetailMapper> config) {
        super(config);
    }
}
