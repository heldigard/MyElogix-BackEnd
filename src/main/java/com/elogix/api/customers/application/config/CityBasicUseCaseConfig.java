package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.gateways.CityBasicGateway;
import com.elogix.api.customers.domain.usecase.CityBasicUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.CityBasicMapper;

import jakarta.persistence.EntityManager;

@Configuration
public class CityBasicUseCaseConfig {
    @Bean
    public CityBasicGatewayImpl cityBasicGatewayImpl(
            CityBasicDataJpaRepository repository,
            CityBasicMapper mapper,
            EntityManager entityManager
    ) {
        return new CityBasicGatewayImpl(
                repository,
                mapper,
                entityManager,
                "deletedCityBasicFilter"
        );
    }

    @Bean
    public CityBasicUseCase cityBasicUseCase(CityBasicGateway gateway) {
        return new CityBasicUseCase(gateway);
    }

}
