package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.gateways.CityGateway;
import com.elogix.api.customers.domain.usecase.CityUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.CityMapper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class CityUseCaseConfig {
    @Bean
    public CityGatewayImpl cityGatewayImpl(
            CityDataJpaRepository repository,
            CityMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new CityGatewayImpl(
                repository,
                mapper,
                entityManager,
                updateUtils,
                "deletedCityFilter");
    }

    @Bean
    public CityUseCase cityUseCase(CityGateway gateway) {
        return new CityUseCase(gateway);
    }

}
