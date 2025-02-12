package com.elogix.api.product_order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.product.domain.usecase.ProductUseCase;
import com.elogix.api.product_order.domain.gateway.ProductOrderGateway;
import com.elogix.api.product_order.domain.usecase.ProductOrderUseCase;
import com.elogix.api.product_order.infrastructure.helper.ProductOrderMapper;
import com.elogix.api.product_order.infrastructure.repository.ProductOrderDataJpaRepository;
import com.elogix.api.product_order.infrastructure.repository.ProductOrderGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

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
