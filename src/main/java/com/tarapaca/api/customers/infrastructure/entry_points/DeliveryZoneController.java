package com.tarapaca.api.customers.infrastructure.entry_points;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.customers.domain.model.DeliveryZone;
import com.tarapaca.api.customers.domain.model.gateways.DeliveryZoneGateway;
import com.tarapaca.api.customers.domain.usecase.DeliveryZoneUseCase;
import com.tarapaca.api.generics.infrastructure.entry_points.GenericNamedController;

@RestController
@RequestMapping("/api/v1/delivery-zone")
public class DeliveryZoneController
        extends GenericNamedController<DeliveryZone, DeliveryZoneGateway, DeliveryZoneUseCase> {

    public DeliveryZoneController(DeliveryZoneUseCase useCase) {
        super(useCase);
    }
}
