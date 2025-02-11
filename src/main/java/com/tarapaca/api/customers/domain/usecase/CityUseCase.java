package com.tarapaca.api.customers.domain.usecase;

import com.tarapaca.api.customers.domain.model.City;
import com.tarapaca.api.customers.domain.model.gateways.CityGateway;
import com.tarapaca.api.generics.domain.usecase.GenericNamedUseCase;

public class CityUseCase
        extends GenericNamedUseCase<City, CityGateway> {

    public CityUseCase(CityGateway gateway) {
        super(gateway);
    }
}
