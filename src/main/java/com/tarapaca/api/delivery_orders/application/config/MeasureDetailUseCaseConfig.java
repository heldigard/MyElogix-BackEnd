package com.tarapaca.api.delivery_orders.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.delivery_orders.domain.model.gateways.MeasureDetailGateway;
import com.tarapaca.api.delivery_orders.domain.usecase.MeasureDetailUseCase;
import com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.measure_detail.MeasureDetailDataJpaRepository;
import com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.measure_detail.MeasureDetailGatewayImpl;
import com.tarapaca.api.delivery_orders.infrastructure.helpers.mappers.MeasureDetailMapper;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class MeasureDetailUseCaseConfig {
    @Bean
    public MeasureDetailGatewayImpl measureGatewayImpl(
            MeasureDetailDataJpaRepository repository,
            MeasureDetailMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new MeasureDetailGatewayImpl(
                repository,
                mapper,
                entityManager,
                updateUtils,
                "deletedMeasureDetailFilter");
    }

    @Bean
    public MeasureDetailUseCase measureUseCase(MeasureDetailGateway gateway) {
        return new MeasureDetailUseCase(gateway);
    }

}
