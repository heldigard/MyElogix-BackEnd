package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.DocumentType;
import com.elogix.api.customers.domain.model.gateways.DocumentTypeGateway;
import com.elogix.api.customers.domain.usecase.DocumentTypeUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.DocumentTypeMapper;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class DocumentTypeUseCaseConfig {
    public static final String DELETED_FILTER = "deletedDocumentTypeFilter";

    @Bean
    public DocumentTypeGatewayImpl documentTypeGatewayImpl(
            DocumentTypeDataJpaRepository repository,
            DocumentTypeMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {

        GenericBasicConfig<DocumentType, DocumentTypeData, DocumentTypeDataJpaRepository, DocumentTypeMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<DocumentType, DocumentTypeData, DocumentTypeDataJpaRepository, DocumentTypeMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new DocumentTypeGatewayImpl(genericConfig);
    }

    private GenericBasicConfig<DocumentType, DocumentTypeData, DocumentTypeDataJpaRepository, DocumentTypeMapper> createBasicConfig(
            DocumentTypeDataJpaRepository repository,
            DocumentTypeMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public DocumentTypeUseCase documentTypeUseCase(DocumentTypeGateway gateway) {
        return new DocumentTypeUseCase(gateway);
    }

}
