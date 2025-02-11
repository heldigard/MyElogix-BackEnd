package com.tarapaca.api.delivery_order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.delivery_order.domain.gateway.DeliveryOrderBasicGateway;
import com.tarapaca.api.delivery_order.domain.usecase.DeliveryOrderBasicUseCase;
import com.tarapaca.api.delivery_order.infrastructure.helper.mapper.DeliveryOrderBasicMapper;
import com.tarapaca.api.delivery_order.infrastructure.repository.delivery_order_basic.DeliveryOrderBasicDataJpaRepository;
import com.tarapaca.api.delivery_order.infrastructure.repository.delivery_order_basic.DeliveryOrderBasicGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.RequestUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class DeliveryOrderBasicUseCaseConfig {
    @Bean
    public DeliveryOrderBasicGatewayImpl deliveryOrderBasicGatewayImpl(
            DeliveryOrderBasicDataJpaRepository repository,
            DeliveryOrderBasicMapper mapper,
            EntityManager entityManager,
            RequestUtils requestUtils
    ) {
        return new DeliveryOrderBasicGatewayImpl(
                repository,
                mapper,
                entityManager,
                requestUtils,
                "deletedDeliveryOrderBasicFilter"
        );
    }

    @Bean
    public DeliveryOrderBasicUseCase deliveryOrderBasicUseCase(DeliveryOrderBasicGateway gateway) {
        return new DeliveryOrderBasicUseCase(gateway);
    }
}
