package com.tarapaca.api.customers.domain.usecase;

import com.tarapaca.api.customers.domain.model.CityBasic;
import com.tarapaca.api.customers.domain.model.gateways.CityBasicGateway;
import com.tarapaca.api.generics.domain.usecase.GenericNamedBasicUseCase;

public class CityBasicUseCase
        extends GenericNamedBasicUseCase<CityBasic, CityBasicGateway> {

    public CityBasicUseCase(CityBasicGateway gateway) {
        super(gateway);
    }
}
