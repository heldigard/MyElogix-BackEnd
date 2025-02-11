package com.tarapaca.api.customers.domain.usecase;

import com.tarapaca.api.customers.domain.model.Neighborhood;
import com.tarapaca.api.customers.domain.model.gateways.NeighborhoodGateway;
import com.tarapaca.api.generics.domain.usecase.GenericNamedUseCase;

public class NeighborhoodUseCase
        extends GenericNamedUseCase<Neighborhood, NeighborhoodGateway> {

    public NeighborhoodUseCase(NeighborhoodGateway gateway) {
        super(gateway);
    }
}
