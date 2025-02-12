package com.elogix.api.customers.domain.usecase;

import com.elogix.api.customers.domain.model.Neighborhood;
import com.elogix.api.customers.domain.model.gateways.NeighborhoodGateway;
import com.elogix.api.generics.domain.usecase.GenericNamedUseCase;

public class NeighborhoodUseCase
        extends GenericNamedUseCase<Neighborhood, NeighborhoodGateway> {

    public NeighborhoodUseCase(NeighborhoodGateway gateway) {
        super(gateway);
    }
}
