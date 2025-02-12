package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.gateways.NeighborhoodGateway;
import com.elogix.api.customers.domain.usecase.CityBasicUseCase;
import com.elogix.api.customers.domain.usecase.DeliveryZoneBasicUseCase;
import com.elogix.api.customers.domain.usecase.NeighborhoodUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.NeighborhoodMapper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class NeighborhoodUseCaseConfig {
    @Bean
    public NeighborhoodGatewayImpl neighborhoodGatewayImpl(
            NeighborhoodDataJpaRepository repository,
            NeighborhoodMapper mapper,
            CityBasicUseCase cityUseCase,
            DeliveryZoneBasicUseCase zoneUseCase,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new NeighborhoodGatewayImpl(
                repository,
                mapper,
                cityUseCase,
                zoneUseCase,
                entityManager,
                updateUtils,
                "deletedNeighborhoodFilter");
    }

    @Bean
    public NeighborhoodUseCase neighborhoodUseCase(NeighborhoodGateway gateway) {
        return new NeighborhoodUseCase(gateway);
    }
}
