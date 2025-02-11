package com.tarapaca.api.customers.infrastructure.entry_points.Neighborhood;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.customers.domain.model.Neighborhood;
import com.tarapaca.api.customers.domain.model.gateways.NeighborhoodGateway;
import com.tarapaca.api.customers.domain.usecase.NeighborhoodUseCase;
import com.tarapaca.api.generics.infrastructure.entry_points.GenericNamedController;

@RestController
@RequestMapping("/api/v1/neighborhood")
public class NeighborhoodController
        extends GenericNamedController<Neighborhood, NeighborhoodGateway, NeighborhoodUseCase> {

    public NeighborhoodController(NeighborhoodUseCase useCase) {
        super(useCase);
    }

}
