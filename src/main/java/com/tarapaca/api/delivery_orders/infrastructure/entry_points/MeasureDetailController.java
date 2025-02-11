package com.tarapaca.api.delivery_orders.infrastructure.entry_points;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.delivery_orders.domain.model.MeasureDetail;
import com.tarapaca.api.delivery_orders.domain.model.gateways.MeasureDetailGateway;
import com.tarapaca.api.delivery_orders.domain.usecase.MeasureDetailUseCase;
import com.tarapaca.api.generics.infrastructure.entry_points.GenericNamedController;

@RestController
@RequestMapping("/api/v1/measure-detail")
public class MeasureDetailController
        extends GenericNamedController<MeasureDetail, MeasureDetailGateway, MeasureDetailUseCase> {
    public MeasureDetailController(MeasureDetailUseCase useCase) {
        super(useCase);
    }
}
