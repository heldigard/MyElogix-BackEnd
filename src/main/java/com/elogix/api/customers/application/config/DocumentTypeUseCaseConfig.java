package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.gateways.DocumentTypeGateway;
import com.elogix.api.customers.domain.usecase.DocumentTypeUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.DocumentTypeMapper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class DocumentTypeUseCaseConfig {
    @Bean
    public DocumentTypeGatewayImpl documentTypeGatewayImpl(
            DocumentTypeDataJpaRepository repository,
            DocumentTypeMapper mapper,
            UpdateUtils updateUtils,
            EntityManager entityManager) {
        return new DocumentTypeGatewayImpl(
                repository,
                mapper,
                entityManager,
                updateUtils,
                "deletedDocumentTypeFilter");
    }

    @Bean
    public DocumentTypeUseCase documentTypeUseCase(DocumentTypeGateway gateway) {
        return new DocumentTypeUseCase(gateway);
    }

}
