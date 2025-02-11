package com.tarapaca.api.customers.application.config;

import com.tarapaca.api.customers.domain.model.gateways.DeliveryZoneBasicGateway;
import com.tarapaca.api.customers.domain.usecase.DeliveryZoneBasicUseCase;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicDataJpaRepository;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicGatewayImpl;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.DeliveryZoneBasicMapper;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeliveryZoneBasicUseCaseConfig {
    @Bean
    public DeliveryZoneBasicGatewayImpl deliveryZoneBasicGatewayImpl(
            DeliveryZoneBasicDataJpaRepository repository,
            DeliveryZoneBasicMapper mapper,
            EntityManager entityManager
    ) {
        return new DeliveryZoneBasicGatewayImpl(
                repository,
                mapper,
                entityManager,
                "deletedDeliveryZoneBasicFilter"
        );
    }

    @Bean
    public DeliveryZoneBasicUseCase deliveryZoneBasicUseCase(DeliveryZoneBasicGateway gateway) {
        return new DeliveryZoneBasicUseCase(gateway);
    }
}
