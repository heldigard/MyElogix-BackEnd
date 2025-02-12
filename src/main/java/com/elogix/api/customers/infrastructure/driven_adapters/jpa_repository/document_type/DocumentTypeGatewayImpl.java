package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.document_type;

import com.elogix.api.customers.domain.model.DocumentType;
import com.elogix.api.customers.domain.model.gateways.DocumentTypeGateway;
import com.elogix.api.customers.infrastructure.helpers.mappers.DocumentTypeMapper;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

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
