package com.tarapaca.api.customers.domain.usecase;

import com.tarapaca.api.customers.domain.model.DocumentType;
import com.tarapaca.api.customers.domain.model.gateways.DocumentTypeGateway;
import com.tarapaca.api.generics.domain.usecase.GenericNamedUseCase;

public class DocumentTypeUseCase
        extends GenericNamedUseCase<DocumentType, DocumentTypeGateway> {

    public DocumentTypeUseCase(DocumentTypeGateway gateway) {
        super(gateway);
    }
}
