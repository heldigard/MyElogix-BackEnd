package com.elogix.api.delivery_orders.domain.usecase;

import com.elogix.api.delivery_orders.domain.model.MeasureDetail;
import com.elogix.api.delivery_orders.domain.model.gateways.MeasureDetailGateway;
import com.elogix.api.generics.domain.usecase.GenericNamedUseCase;

public class MeasureDetailUseCase
        extends GenericNamedUseCase<MeasureDetail, MeasureDetailGateway> {

    public MeasureDetailUseCase(MeasureDetailGateway gateway) {
        super(gateway);
    }
}
