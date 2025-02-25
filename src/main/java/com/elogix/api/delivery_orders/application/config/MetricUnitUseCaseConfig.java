package com.elogix.api.delivery_orders.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.delivery_orders.domain.model.MetricUnit;
import com.elogix.api.delivery_orders.domain.model.gateways.MetricUnitGateway;
import com.elogix.api.delivery_orders.domain.usecase.MetricUnitUseCase;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit.MetricUnitData;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit.MetricUnitDataJpaRepository;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit.MetricUnitGatewayImpl;
import com.elogix.api.delivery_orders.infrastructure.helpers.mappers.MetricUnitMapper;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class MetricUnitUseCaseConfig {
    public static final String DELETED_FILTER = "deletedMetricUnitFilter";

    @Bean
    public MetricUnitGatewayImpl metricUnitGatewayImpl(
            MetricUnitDataJpaRepository repository,
            MetricUnitMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {

        GenericBasicConfig<MetricUnit, MetricUnitData, MetricUnitDataJpaRepository, MetricUnitMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<MetricUnit, MetricUnitData, MetricUnitDataJpaRepository, MetricUnitMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new MetricUnitGatewayImpl(genericConfig);
    }

    private GenericBasicConfig<MetricUnit, MetricUnitData, MetricUnitDataJpaRepository, MetricUnitMapper> createBasicConfig(
            MetricUnitDataJpaRepository repository,
            MetricUnitMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public MetricUnitUseCase metricUnitUseCase(MetricUnitGateway gateway) {
        return new MetricUnitUseCase(gateway);
    }

}
