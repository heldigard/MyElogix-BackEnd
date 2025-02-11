package com.tarapaca.api.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.product.domain.gateway.ProductCategoryGateway;
import com.tarapaca.api.product.domain.usecase.ProductCategoryUseCase;
import com.tarapaca.api.product.infrastructure.helper.mapper.ProductCategoryMapper;
import com.tarapaca.api.product.infrastructure.repository.product_category.ProductCategoryDataJpaRepository;
import com.tarapaca.api.product.infrastructure.repository.product_category.ProductCategoryGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class ProductCategoryUseCaseConfig {
    @Bean
    public ProductCategoryGatewayImpl productCategoryGatewayImpl(
            ProductCategoryDataJpaRepository repository,
            ProductCategoryMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new ProductCategoryGatewayImpl(
                repository,
                mapper,
                entityManager,
                updateUtils,
                "deletedProductCategoryFilter");
    }

    @Bean
    public ProductCategoryUseCase productCategoryUseCase(ProductCategoryGateway gateway) {
        return new ProductCategoryUseCase(gateway);
    }
}
