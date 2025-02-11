package com.tarapaca.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.customers.domain.model.gateways.DocumentTypeGateway;
import com.tarapaca.api.customers.domain.usecase.DocumentTypeUseCase;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeDataJpaRepository;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeGatewayImpl;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.DocumentTypeMapper;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

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
