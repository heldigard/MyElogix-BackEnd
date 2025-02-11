package com.tarapaca.api.delivery_orders.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.delivery_orders.domain.model.gateways.StatusGateway;
import com.tarapaca.api.delivery_orders.domain.usecase.StatusUseCase;
import com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status.StatusDataJpaRepository;
import com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status.StatusGatewayImpl;
import com.tarapaca.api.delivery_orders.infrastructure.helpers.mappers.StatusMapper;

import jakarta.persistence.EntityManager;

@Configuration
public class StatusUseCaseConfig {
    @Bean
    public StatusGatewayImpl statusGatewayImpl(
            StatusDataJpaRepository repository,
            StatusMapper mapper,
            EntityManager entityManager
    ) {
        return new StatusGatewayImpl(
                repository,
                mapper,
                entityManager,
                "deletedStatusFilter"
        );
    }

    @Bean
    public StatusUseCase statusUseCase(StatusGateway gateway) {
        return new StatusUseCase(gateway);
    }
}
