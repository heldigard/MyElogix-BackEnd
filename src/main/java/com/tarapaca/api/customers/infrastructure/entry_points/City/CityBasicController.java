package com.tarapaca.api.customers.infrastructure.entry_points.City;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.customers.domain.model.CityBasic;
import com.tarapaca.api.customers.domain.model.gateways.CityBasicGateway;
import com.tarapaca.api.customers.domain.usecase.CityBasicUseCase;
import com.tarapaca.api.generics.infrastructure.entry_points.GenericNamedBasicController;

@RestController
@RequestMapping("/api/v1/city-basic")
public class CityBasicController extends GenericNamedBasicController<CityBasic, CityBasicGateway, CityBasicUseCase> {

    public CityBasicController(CityBasicUseCase useCase) {
        super(useCase);
    }
}
