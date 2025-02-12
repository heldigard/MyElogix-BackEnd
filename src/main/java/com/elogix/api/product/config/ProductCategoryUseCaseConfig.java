package com.elogix.api.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.product.domain.gateway.ProductCategoryGateway;
import com.elogix.api.product.domain.usecase.ProductCategoryUseCase;
import com.elogix.api.product.infrastructure.helper.mapper.ProductCategoryMapper;
import com.elogix.api.product.infrastructure.repository.product_category.ProductCategoryDataJpaRepository;
import com.elogix.api.product.infrastructure.repository.product_category.ProductCategoryGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

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
