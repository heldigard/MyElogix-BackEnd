package com.elogix.api.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.product.domain.gateway.ProductPriceGateway;
import com.elogix.api.product.domain.model.ProductPrice;
import com.elogix.api.product.domain.usecase.ProductPriceUseCase;
import com.elogix.api.product.infrastructure.helper.mapper.ProductPriceMapper;
import com.elogix.api.product.infrastructure.repository.product_price.ProductPriceData;
import com.elogix.api.product.infrastructure.repository.product_price.ProductPriceDataJpaRepository;
import com.elogix.api.product.infrastructure.repository.product_price.ProductPriceGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class ProductPriceUseCaseConfig {
    public static final String DELETED_FILTER = "deletedProductPriceFilter";

    @Bean
    public ProductPriceGatewayImpl productPriceGatewayImpl(
            ProductPriceDataJpaRepository repository,
            ProductPriceMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {

        GenericBasicConfig<ProductPrice, ProductPriceData, ProductPriceDataJpaRepository, ProductPriceMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<ProductPrice, ProductPriceData, ProductPriceDataJpaRepository, ProductPriceMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new ProductPriceGatewayImpl(genericConfig);
    }

    private GenericBasicConfig<ProductPrice, ProductPriceData, ProductPriceDataJpaRepository, ProductPriceMapper> createBasicConfig(
            ProductPriceDataJpaRepository repository,
            ProductPriceMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public ProductPriceUseCase productPriceUseCase(ProductPriceGateway gateway) {
        return new ProductPriceUseCase(gateway);
    }
}
