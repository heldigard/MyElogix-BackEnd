package com.elogix.api.users.infrastructure.entry_points;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.generics.infrastructure.entry_points.GenericNamedController;
import com.elogix.api.users.domain.model.Office;
import com.elogix.api.users.domain.model.gateways.OfficeGateway;
import com.elogix.api.users.domain.usecase.OfficeUseCase;

@RestController
@RequestMapping("/api/v1/office")
public class OfficeController extends GenericNamedController<Office, OfficeGateway, OfficeUseCase> {
    public OfficeController(OfficeUseCase useCase) {
        super(useCase);
    }
}
