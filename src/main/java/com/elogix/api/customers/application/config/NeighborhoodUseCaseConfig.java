package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.Neighborhood;
import com.elogix.api.customers.domain.model.gateways.NeighborhoodGateway;
import com.elogix.api.customers.domain.usecase.CityBasicUseCase;
import com.elogix.api.customers.domain.usecase.DeliveryZoneBasicUseCase;
import com.elogix.api.customers.domain.usecase.NeighborhoodUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.NeighborhoodMapper;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class NeighborhoodUseCaseConfig {
    public static final String DELETED_FILTER = "deletedNeighborhoodFilter";

    @Bean
    public NeighborhoodGatewayImpl neighborhoodGatewayImpl(
            NeighborhoodDataJpaRepository repository,
            NeighborhoodMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            CityBasicUseCase cityUseCase,
            DeliveryZoneBasicUseCase zoneUseCase) {

        GenericBasicConfig<Neighborhood, NeighborhoodData, NeighborhoodDataJpaRepository, NeighborhoodMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<Neighborhood, NeighborhoodData, NeighborhoodDataJpaRepository, NeighborhoodMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new NeighborhoodGatewayImpl(genericConfig, cityUseCase, zoneUseCase);
    }

    private GenericBasicConfig<Neighborhood, NeighborhoodData, NeighborhoodDataJpaRepository, NeighborhoodMapper> createBasicConfig(
            NeighborhoodDataJpaRepository repository,
            NeighborhoodMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public NeighborhoodUseCase neighborhoodUseCase(NeighborhoodGateway gateway) {
        return new NeighborhoodUseCase(gateway);
    }
}
