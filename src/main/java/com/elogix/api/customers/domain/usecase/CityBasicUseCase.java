package com.elogix.api.customers.domain.usecase;

import com.elogix.api.customers.domain.model.CityBasic;
import com.elogix.api.customers.domain.model.gateways.CityBasicGateway;
import com.elogix.api.generics.domain.usecase.GenericNamedBasicUseCase;

public class CityBasicUseCase
        extends GenericNamedBasicUseCase<CityBasic, CityBasicGateway> {

    public CityBasicUseCase(CityBasicGateway gateway) {
        super(gateway);
    }
}
