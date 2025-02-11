package com.tarapaca.api.customers.infrastructure.entry_points;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.customers.domain.model.ContactPerson;
import com.tarapaca.api.customers.domain.model.gateways.ContactPersonGateway;
import com.tarapaca.api.customers.domain.usecase.ContactPersonUseCase;
import com.tarapaca.api.generics.infrastructure.entry_points.GenericNamedController;

@RestController
@RequestMapping("/api/v1/contactperson")
public class ContactPersonController
        extends GenericNamedController<ContactPerson, ContactPersonGateway, ContactPersonUseCase> {

    public ContactPersonController(ContactPersonUseCase useCase) {
        super(useCase);
    }
}
