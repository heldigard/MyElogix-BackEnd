package com.elogix.api.delivery_orders.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.delivery_orders.domain.model.MeasureDetail;
import com.elogix.api.delivery_orders.domain.model.gateways.MeasureDetailGateway;
import com.elogix.api.delivery_orders.domain.usecase.MeasureDetailUseCase;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.measure_detail.MeasureDetailData;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.measure_detail.MeasureDetailDataJpaRepository;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.measure_detail.MeasureDetailGatewayImpl;
import com.elogix.api.delivery_orders.infrastructure.helpers.mappers.MeasureDetailMapper;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class MeasureDetailUseCaseConfig {
    public static final String DELETED_FILTER = "deletedMeasureDetailFilter";

    @Bean
    public MeasureDetailGatewayImpl measureGatewayImpl(
            MeasureDetailDataJpaRepository repository,
            MeasureDetailMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {

        GenericBasicConfig<MeasureDetail, MeasureDetailData, MeasureDetailDataJpaRepository, MeasureDetailMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<MeasureDetail, MeasureDetailData, MeasureDetailDataJpaRepository, MeasureDetailMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new MeasureDetailGatewayImpl(genericConfig);
    }

    private GenericBasicConfig<MeasureDetail, MeasureDetailData, MeasureDetailDataJpaRepository, MeasureDetailMapper> createBasicConfig(
            MeasureDetailDataJpaRepository repository,
            MeasureDetailMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public MeasureDetailUseCase measureUseCase(MeasureDetailGateway gateway) {
        return new MeasureDetailUseCase(gateway);
    }
}
