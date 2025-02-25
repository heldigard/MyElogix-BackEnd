package com.elogix.api.delivery_orders.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.delivery_orders.domain.model.PriceList;
import com.elogix.api.delivery_orders.domain.model.gateways.PriceListGateway;
import com.elogix.api.delivery_orders.domain.usecase.PriceListUseCase;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.price_list.PriceListData;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.price_list.PriceListDataJpaRepository;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.price_list.PriceListGatewayImpl;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.product.infrastructure.helper.mapper.PriceListMapper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

/**
 * Configuration class for Price List related beans.
 * Provides dependency injection setup for Price List use cases and gateways.
 */
@Configuration
public class PriceListUseCaseConfig {
    public static final String DELETED_FILTER = "deletedPriceListFilter";

    @Bean
    public PriceListGatewayImpl priceListGateway(
            PriceListDataJpaRepository repository,
            PriceListMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {

        GenericBasicConfig<PriceList, PriceListData, PriceListDataJpaRepository, PriceListMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<PriceList, PriceListData, PriceListDataJpaRepository, PriceListMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new PriceListGatewayImpl(genericConfig);
    }

    private GenericBasicConfig<PriceList, PriceListData, PriceListDataJpaRepository, PriceListMapper> createBasicConfig(
            PriceListDataJpaRepository repository,
            PriceListMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public PriceListUseCase priceListUseCase(PriceListGateway gateway) {
        return new PriceListUseCase(gateway);
    }
}
