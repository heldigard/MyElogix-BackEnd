package com.elogix.api.customers.infrastructure.entry_points.City;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.customers.domain.model.CityBasic;
import com.elogix.api.customers.domain.model.gateways.CityBasicGateway;
import com.elogix.api.customers.domain.usecase.CityBasicUseCase;
import com.elogix.api.generics.infrastructure.entry_points.GenericNamedBasicController;

@RestController
@RequestMapping("/api/v1/city-basic")
public class CityBasicController extends GenericNamedBasicController<CityBasic, CityBasicGateway, CityBasicUseCase> {

    public CityBasicController(CityBasicUseCase useCase) {
        super(useCase);
    }
}
