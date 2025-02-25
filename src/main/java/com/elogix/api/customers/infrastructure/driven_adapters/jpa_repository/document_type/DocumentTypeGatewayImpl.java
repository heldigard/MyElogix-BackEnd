package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.document_type;

import com.elogix.api.customers.domain.model.DocumentType;
import com.elogix.api.customers.domain.model.gateways.DocumentTypeGateway;
import com.elogix.api.customers.infrastructure.helpers.mappers.DocumentTypeMapper;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;

public class DocumentTypeGatewayImpl
        extends
        GenericNamedGatewayImpl<DocumentType, DocumentTypeData, DocumentTypeDataJpaRepository, DocumentTypeMapper>
        implements DocumentTypeGateway {

    public DocumentTypeGatewayImpl(
            GenericConfig<DocumentType, DocumentTypeData, DocumentTypeDataJpaRepository, DocumentTypeMapper> config) {
        super(config);
    }
}
