package com.elogix.api.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.config.GenericStatusConfig;
import com.elogix.api.product.domain.gateway.ProductGateway;
import com.elogix.api.product.domain.model.Product;
import com.elogix.api.product.domain.usecase.ProductTypeUseCase;
import com.elogix.api.product.domain.usecase.ProductUseCase;
import com.elogix.api.product.infrastructure.helper.mapper.ProductMapper;
import com.elogix.api.product.infrastructure.repository.product.ProductData;
import com.elogix.api.product.infrastructure.repository.product.ProductDataJpaRepository;
import com.elogix.api.product.infrastructure.repository.product.ProductGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class ProductUseCaseConfig {
    public static final String DELETED_FILTER = "deletedProductFilter";

    @Bean
    public ProductGatewayImpl productGatewayImpl(
            ProductDataJpaRepository repository,
            ProductMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            StatusUseCase statusUseCase,
            ProductTypeUseCase productTypeUseCase) {

        GenericBasicConfig<Product, ProductData, ProductDataJpaRepository, ProductMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<Product, ProductData, ProductDataJpaRepository, ProductMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        GenericStatusConfig<Product, ProductData, ProductDataJpaRepository, ProductMapper> statusConfig = new GenericStatusConfig<>(
                genericConfig, statusUseCase);

        return new ProductGatewayImpl(statusConfig, productTypeUseCase);
    }

    private GenericBasicConfig<Product, ProductData, ProductDataJpaRepository, ProductMapper> createBasicConfig(
            ProductDataJpaRepository repository,
            ProductMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public ProductUseCase productUseCase(ProductGateway gateway) {
        return new ProductUseCase(gateway);
    }
}
