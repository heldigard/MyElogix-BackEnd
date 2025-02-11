package com.tarapaca.api.delivery_orders.domain.usecase;

import com.tarapaca.api.delivery_orders.domain.model.MeasureDetail;
import com.tarapaca.api.delivery_orders.domain.model.gateways.MeasureDetailGateway;
import com.tarapaca.api.generics.domain.usecase.GenericNamedUseCase;

public class MeasureDetailUseCase
        extends GenericNamedUseCase<MeasureDetail, MeasureDetailGateway> {

    public MeasureDetailUseCase(MeasureDetailGateway gateway) {
        super(gateway);
    }
}
