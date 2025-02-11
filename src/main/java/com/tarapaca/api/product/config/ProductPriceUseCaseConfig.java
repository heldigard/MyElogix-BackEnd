package com.tarapaca.api.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.product.domain.gateway.ProductPriceGateway;
import com.tarapaca.api.product.domain.usecase.ProductPriceUseCase;
import com.tarapaca.api.product.infrastructure.helper.mapper.ProductPriceMapper;
import com.tarapaca.api.product.infrastructure.repository.product_price.ProductPriceDataJpaRepository;
import com.tarapaca.api.product.infrastructure.repository.product_price.ProductPriceGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class ProductPriceUseCaseConfig {
    @Bean
    public ProductPriceGatewayImpl productPriceGatewayImpl(
            ProductPriceDataJpaRepository repository,
            ProductPriceMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new ProductPriceGatewayImpl(
                repository,
                mapper,
                entityManager,
                updateUtils,
                "deletedProductPriceFilter");
    }

    @Bean
    public ProductPriceUseCase productPriceUseCase(ProductPriceGateway gateway) {
        return new ProductPriceUseCase(gateway);
    }
}
