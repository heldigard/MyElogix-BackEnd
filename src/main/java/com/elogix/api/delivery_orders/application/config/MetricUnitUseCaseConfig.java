package com.elogix.api.delivery_orders.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.delivery_orders.domain.model.gateways.MetricUnitGateway;
import com.elogix.api.delivery_orders.domain.usecase.MetricUnitUseCase;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit.MetricUnitDataJpaRepository;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit.MetricUnitGatewayImpl;
import com.elogix.api.delivery_orders.infrastructure.helpers.mappers.MetricUnitMapper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class MetricUnitUseCaseConfig {
    @Bean
    public MetricUnitGatewayImpl metricUnitGatewayImpl(

            MetricUnitDataJpaRepository repository,
            MetricUnitMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new MetricUnitGatewayImpl(
                repository,
                mapper,
                entityManager,
                updateUtils,
                "deletedMetricUnitFilter");
    }

    @Bean
    public MetricUnitUseCase metricUnitUseCase(MetricUnitGateway gateway) {
        return new MetricUnitUseCase(gateway);
    }

}
