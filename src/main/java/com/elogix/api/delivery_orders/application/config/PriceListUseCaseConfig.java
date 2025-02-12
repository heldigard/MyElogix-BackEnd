package com.elogix.api.delivery_orders.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import com.elogix.api.delivery_orders.domain.model.gateways.PriceListGateway;
import com.elogix.api.delivery_orders.domain.usecase.PriceListUseCase;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.price_list.PriceListDataJpaRepository;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.price_list.PriceListGatewayImpl;
import com.elogix.api.product.infrastructure.helper.mapper.PriceListMapper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

/**
 * Configuration class for Price List related beans.
 * Provides dependency injection setup for Price List use cases and gateways.
 */
@Configuration
public class PriceListUseCaseConfig {
    @Bean
    @NonNull
    public PriceListGateway priceListGateway(
            PriceListDataJpaRepository repository,
            PriceListMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new PriceListGatewayImpl(
                repository,
                mapper,
                entityManager,
                updateUtils,
                "deletedPriceListFilter");
    }

    @Bean
    @NonNull
    public PriceListUseCase priceListUseCase(@NonNull PriceListGateway gateway) {
        return new PriceListUseCase(gateway);
    }
}
