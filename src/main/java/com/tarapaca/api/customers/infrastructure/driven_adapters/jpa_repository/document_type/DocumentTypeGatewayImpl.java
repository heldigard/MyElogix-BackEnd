package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.document_type;

import com.tarapaca.api.customers.domain.model.DocumentType;
import com.tarapaca.api.customers.domain.model.gateways.DocumentTypeGateway;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.DocumentTypeMapper;
import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

public class DocumentTypeGatewayImpl
        extends
        GenericNamedGatewayImpl<DocumentType, DocumentTypeData, DocumentTypeDataJpaRepository, DocumentTypeMapper>
        implements DocumentTypeGateway {

    public DocumentTypeGatewayImpl(
            DocumentTypeDataJpaRepository repository,
            DocumentTypeMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
    }
}
