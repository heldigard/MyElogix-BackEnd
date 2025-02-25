package com.elogix.api.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.product.domain.gateway.ProductTypeGateway;
import com.elogix.api.product.domain.model.ProductType;
import com.elogix.api.product.domain.usecase.ProductTypeUseCase;
import com.elogix.api.product.infrastructure.helper.mapper.ProductTypeMapper;
import com.elogix.api.product.infrastructure.repository.product_category.ProductCategoryDataJpaRepository;
import com.elogix.api.product.infrastructure.repository.product_type.ProductTypeData;
import com.elogix.api.product.infrastructure.repository.product_type.ProductTypeDataJpaRepository;
import com.elogix.api.product.infrastructure.repository.product_type.ProductTypeGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class ProductTypeUseCaseConfig {
    public static final String DELETED_FILTER = "deletedProductTypeFilter";

    @Bean
    public ProductTypeGatewayImpl productTypeGatewayImpl(
            ProductTypeDataJpaRepository repository,
            ProductTypeMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            ProductCategoryDataJpaRepository repoCategory) {

        GenericBasicConfig<ProductType, ProductTypeData, ProductTypeDataJpaRepository, ProductTypeMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<ProductType, ProductTypeData, ProductTypeDataJpaRepository, ProductTypeMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new ProductTypeGatewayImpl(genericConfig, repoCategory);
    }

    private GenericBasicConfig<ProductType, ProductTypeData, ProductTypeDataJpaRepository, ProductTypeMapper> createBasicConfig(
            ProductTypeDataJpaRepository repository,
            ProductTypeMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public ProductTypeUseCase productTypeUseCase(ProductTypeGateway gateway) {
        return new ProductTypeUseCase(gateway);
    }
}
