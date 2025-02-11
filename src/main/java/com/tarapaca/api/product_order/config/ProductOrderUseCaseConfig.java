package com.tarapaca.api.product_order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.delivery_orders.domain.usecase.StatusUseCase;
import com.tarapaca.api.product.domain.usecase.ProductUseCase;
import com.tarapaca.api.product_order.domain.gateway.ProductOrderGateway;
import com.tarapaca.api.product_order.domain.usecase.ProductOrderUseCase;
import com.tarapaca.api.product_order.infrastructure.helper.ProductOrderMapper;
import com.tarapaca.api.product_order.infrastructure.repository.ProductOrderDataJpaRepository;
import com.tarapaca.api.product_order.infrastructure.repository.ProductOrderGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class ProductOrderUseCaseConfig {

    @Bean
    public ProductOrderGatewayImpl productOrderGatewayImpl(
            ProductOrderDataJpaRepository repository,
            ProductOrderMapper mapper,
            EntityManager entityManager,
            StatusUseCase statusUseCase,
            ProductUseCase productUseCase,
            UpdateUtils updateUtils) {
        return new ProductOrderGatewayImpl(
                repository,
                mapper,
                entityManager,
                statusUseCase,
                productUseCase,
                updateUtils,
                "deletedProductOrderFilter");
    }

    @Bean
    public ProductOrderUseCase productOrderUseCase(ProductOrderGateway gateway) {
        return new ProductOrderUseCase(gateway);
    }
}
