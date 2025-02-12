package com.elogix.api.customers.infrastructure.entry_points;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.customers.domain.model.DocumentType;
import com.elogix.api.customers.domain.model.gateways.DocumentTypeGateway;
import com.elogix.api.customers.domain.usecase.DocumentTypeUseCase;
import com.elogix.api.generics.infrastructure.entry_points.GenericNamedController;

@RestController
@RequestMapping("/api/v1/document-type")
public class DocumentTypeController
        extends GenericNamedController<DocumentType, DocumentTypeGateway, DocumentTypeUseCase> {

    public DocumentTypeController(DocumentTypeUseCase useCase) {
        super(useCase);
    }
}
