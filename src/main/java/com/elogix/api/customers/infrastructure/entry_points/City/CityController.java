package com.elogix.api.customers.infrastructure.entry_points.city;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.customers.domain.model.City;
import com.elogix.api.customers.domain.model.gateways.CityGateway;
import com.elogix.api.customers.domain.usecase.CityUseCase;
import com.elogix.api.generics.infrastructure.entry_points.GenericNamedController;

@RestController
@RequestMapping("/api/v1/city")
public class CityController
        extends GenericNamedController<City, CityGateway, CityUseCase> {

    public CityController(CityUseCase useCase) {
        super(useCase);
    }
}
