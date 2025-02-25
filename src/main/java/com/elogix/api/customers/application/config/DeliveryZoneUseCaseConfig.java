package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.DeliveryZone;
import com.elogix.api.customers.domain.model.gateways.DeliveryZoneGateway;
import com.elogix.api.customers.domain.usecase.DeliveryZoneUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone.DeliveryZoneData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone.DeliveryZoneDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone.DeliveryZoneGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.DeliveryZoneMapper;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class DeliveryZoneUseCaseConfig {
    public static final String DELETED_FILTER = "deletedDeliveryZoneFilter";

    @Bean
    public DeliveryZoneGatewayImpl deliveryZoneGatewayImpl(
            DeliveryZoneDataJpaRepository repository,
            DeliveryZoneMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {

        GenericBasicConfig<DeliveryZone, DeliveryZoneData, DeliveryZoneDataJpaRepository, DeliveryZoneMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<DeliveryZone, DeliveryZoneData, DeliveryZoneDataJpaRepository, DeliveryZoneMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new DeliveryZoneGatewayImpl(genericConfig);
    }

    private GenericBasicConfig<DeliveryZone, DeliveryZoneData, DeliveryZoneDataJpaRepository, DeliveryZoneMapper> createBasicConfig(
            DeliveryZoneDataJpaRepository repository,
            DeliveryZoneMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public DeliveryZoneUseCase deliveryZoneUseCase(DeliveryZoneGateway gateway) {
        return new DeliveryZoneUseCase(gateway);
    }

}
