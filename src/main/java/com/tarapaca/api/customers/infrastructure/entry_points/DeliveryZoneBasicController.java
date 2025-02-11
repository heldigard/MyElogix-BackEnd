package com.tarapaca.api.customers.infrastructure.entry_points;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.customers.domain.model.DeliveryZoneBasic;
import com.tarapaca.api.customers.domain.model.gateways.DeliveryZoneBasicGateway;
import com.tarapaca.api.customers.domain.usecase.DeliveryZoneBasicUseCase;
import com.tarapaca.api.generics.infrastructure.entry_points.GenericNamedBasicController;

@RestController
@RequestMapping("/api/v1/delivery-zone-basic")
public class DeliveryZoneBasicController
        extends GenericNamedBasicController<DeliveryZoneBasic, DeliveryZoneBasicGateway, DeliveryZoneBasicUseCase> {
    public DeliveryZoneBasicController(DeliveryZoneBasicUseCase useCase) {
        super(useCase);
    }
}
