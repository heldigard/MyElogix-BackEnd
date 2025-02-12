package com.elogix.api.customers.domain.usecase;

import com.elogix.api.customers.domain.model.City;
import com.elogix.api.customers.domain.model.gateways.CityGateway;
import com.elogix.api.generics.domain.usecase.GenericNamedUseCase;

public class CityUseCase
        extends GenericNamedUseCase<City, CityGateway> {

    public CityUseCase(CityGateway gateway) {
        super(gateway);
    }
}
