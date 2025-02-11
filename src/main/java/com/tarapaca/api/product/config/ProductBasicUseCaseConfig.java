package com.tarapaca.api.product.config;

import com.tarapaca.api.product.domain.gateway.ProductBasicGateway;
import com.tarapaca.api.product.domain.usecase.ProductBasicUseCase;
import com.tarapaca.api.product.infrastructure.repository.product_basic.ProductBasicDataJpaRepository;
import com.tarapaca.api.product.infrastructure.repository.product_basic.ProductBasicGatewayImpl;
import com.tarapaca.api.product.infrastructure.helper.mapper.ProductBasicMapper;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductBasicUseCaseConfig {
    @Bean
    public ProductBasicGatewayImpl productBasicGatewayImpl(
            ProductBasicDataJpaRepository repository,
            ProductBasicMapper mapper,
            EntityManager entityManager
    ) {
        return new ProductBasicGatewayImpl(
                repository,
                mapper,
                entityManager,
                "deletedProductBasicFilter"
        );
    }

    @Bean
    public ProductBasicUseCase productBasicUseCase(ProductBasicGateway gateway) {
        return new ProductBasicUseCase(gateway);
    }
}
