package com.elogix.api.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.product.domain.gateway.ProductGateway;
import com.elogix.api.product.domain.usecase.ProductTypeUseCase;
import com.elogix.api.product.domain.usecase.ProductUseCase;
import com.elogix.api.product.infrastructure.helper.mapper.ProductMapper;
import com.elogix.api.product.infrastructure.repository.product.ProductDataJpaRepository;
import com.elogix.api.product.infrastructure.repository.product.ProductGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class ProductUseCaseConfig {
    @Bean
    public ProductGatewayImpl productGatewayImpl(
            ProductDataJpaRepository repository,
            ProductMapper mapper,
            ProductTypeUseCase productTypeUseCase,
            StatusUseCase statusUseCase,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new ProductGatewayImpl(
                repository,
                mapper,
                productTypeUseCase,
                statusUseCase,
                entityManager,
                updateUtils,
                "deletedProductFilter");
    }

    @Bean
    public ProductUseCase productUseCase(ProductGateway gateway) {
        return new ProductUseCase(gateway);
    }
}
