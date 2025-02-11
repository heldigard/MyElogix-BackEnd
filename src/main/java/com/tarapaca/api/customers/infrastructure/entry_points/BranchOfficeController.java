package com.tarapaca.api.customers.infrastructure.entry_points;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.customers.domain.model.BranchOffice;
import com.tarapaca.api.customers.domain.model.gateways.BranchOfficeGateway;
import com.tarapaca.api.customers.domain.usecase.BranchOfficeUseCase;
import com.tarapaca.api.generics.infrastructure.entry_points.GenericController;

@RestController
@RequestMapping("/api/v1/branchOffice")
public class BranchOfficeController extends GenericController<BranchOffice, BranchOfficeGateway, BranchOfficeUseCase> {

    public BranchOfficeController(BranchOfficeUseCase useCase) {
        super(useCase);
    }
}
