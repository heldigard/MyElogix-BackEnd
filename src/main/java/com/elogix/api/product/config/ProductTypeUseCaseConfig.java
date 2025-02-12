package com.elogix.api.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.product.domain.gateway.ProductTypeGateway;
import com.elogix.api.product.domain.usecase.ProductTypeUseCase;
import com.elogix.api.product.infrastructure.helper.mapper.ProductTypeMapper;
import com.elogix.api.product.infrastructure.repository.product_category.ProductCategoryDataJpaRepository;
import com.elogix.api.product.infrastructure.repository.product_type.ProductTypeDataJpaRepository;
import com.elogix.api.product.infrastructure.repository.product_type.ProductTypeGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class ProductTypeUseCaseConfig {
    @Bean
    public ProductTypeGatewayImpl productTypeGatewayImpl(
            ProductTypeDataJpaRepository repository,
            ProductTypeMapper mapper,
            ProductCategoryDataJpaRepository repoCategory,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new ProductTypeGatewayImpl(
                repository,
                mapper,
                repoCategory,
                entityManager,
                updateUtils,
                "deletedProductTypeFilter");
    }

    @Bean
    public ProductTypeUseCase productTypeUseCase(ProductTypeGateway gateway) {
        return new ProductTypeUseCase(gateway);
    }
}
