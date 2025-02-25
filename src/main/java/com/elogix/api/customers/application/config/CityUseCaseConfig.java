package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.City;
import com.elogix.api.customers.domain.model.gateways.CityGateway;
import com.elogix.api.customers.domain.usecase.CityUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.CityMapper;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class CityUseCaseConfig {
    public static final String DELETED_FILTER = "deletedCityFilter";

    @Bean
    public CityGatewayImpl cityGatewayImpl(
            CityDataJpaRepository repository,
            CityMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {

        GenericBasicConfig<City, CityData, CityDataJpaRepository, CityMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<City, CityData, CityDataJpaRepository, CityMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new CityGatewayImpl(genericConfig);
    }

    private GenericBasicConfig<City, CityData, CityDataJpaRepository, CityMapper> createBasicConfig(
            CityDataJpaRepository repository,
            CityMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public CityUseCase cityUseCase(CityGateway gateway) {
        return new CityUseCase(gateway);
    }

}
