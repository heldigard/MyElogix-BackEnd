package com.elogix.api.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.product.domain.gateway.ProductCategoryGateway;
import com.elogix.api.product.domain.model.ProductCategory;
import com.elogix.api.product.domain.usecase.ProductCategoryUseCase;
import com.elogix.api.product.infrastructure.helper.mapper.ProductCategoryMapper;
import com.elogix.api.product.infrastructure.repository.product_category.ProductCategoryData;
import com.elogix.api.product.infrastructure.repository.product_category.ProductCategoryDataJpaRepository;
import com.elogix.api.product.infrastructure.repository.product_category.ProductCategoryGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class ProductCategoryUseCaseConfig {
    public static final String DELETED_FILTER = "deletedProductCategoryFilter";

    @Bean
    public ProductCategoryGatewayImpl productCategoryGatewayImpl(
            ProductCategoryDataJpaRepository repository,
            ProductCategoryMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {

        GenericBasicConfig<ProductCategory, ProductCategoryData, ProductCategoryDataJpaRepository, ProductCategoryMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<ProductCategory, ProductCategoryData, ProductCategoryDataJpaRepository, ProductCategoryMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new ProductCategoryGatewayImpl(genericConfig);
    }

    private GenericBasicConfig<ProductCategory, ProductCategoryData, ProductCategoryDataJpaRepository, ProductCategoryMapper> createBasicConfig(
            ProductCategoryDataJpaRepository repository,
            ProductCategoryMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public ProductCategoryUseCase productCategoryUseCase(ProductCategoryGateway gateway) {
        return new ProductCategoryUseCase(gateway);
    }
}
