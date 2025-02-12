package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.gateways.DeliveryZoneGateway;
import com.elogix.api.customers.domain.usecase.DeliveryZoneUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone.DeliveryZoneDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone.DeliveryZoneGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.DeliveryZoneMapper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class DeliveryZoneUseCaseConfig {
    @Bean
    public DeliveryZoneGatewayImpl deliveryZoneGatewayImpl(
            DeliveryZoneDataJpaRepository repository,
            DeliveryZoneMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new DeliveryZoneGatewayImpl(
                repository,
                mapper,
                entityManager,
                updateUtils,
                "deletedDeliveryZoneFilter");
    }

    @Bean
    public DeliveryZoneUseCase deliveryZoneUseCase(DeliveryZoneGateway gateway) {
        return new DeliveryZoneUseCase(gateway);
    }

}
