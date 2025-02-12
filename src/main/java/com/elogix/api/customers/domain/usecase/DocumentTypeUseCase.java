package com.elogix.api.customers.domain.usecase;

import com.elogix.api.customers.domain.model.DocumentType;
import com.elogix.api.customers.domain.model.gateways.DocumentTypeGateway;
import com.elogix.api.generics.domain.usecase.GenericNamedUseCase;

public class DocumentTypeUseCase
        extends GenericNamedUseCase<DocumentType, DocumentTypeGateway> {

    public DocumentTypeUseCase(DocumentTypeGateway gateway) {
        super(gateway);
    }
}
