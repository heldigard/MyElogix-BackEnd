package com.elogix.api.product_order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.usecase.BranchOfficeUseCase;
import com.elogix.api.customers.domain.usecase.CustomerUseCase;
import com.elogix.api.customers.domain.usecase.DeliveryZoneBasicUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerDataJpaRepository;
import com.elogix.api.delivery_orders.domain.usecase.MeasureDetailUseCase;
import com.elogix.api.delivery_orders.domain.usecase.MetricUnitUseCase;
import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.config.GenericProductionConfig;
import com.elogix.api.generics.config.GenericStatusConfig;
import com.elogix.api.product.domain.usecase.ProductBasicUseCase;
import com.elogix.api.product.domain.usecase.ProductUseCase;
import com.elogix.api.product_order.domain.gateway.ProductOrderGateway;
import com.elogix.api.product_order.domain.model.ProductOrder;
import com.elogix.api.product_order.domain.usecase.ProductOrderUseCase;
import com.elogix.api.product_order.infrastructure.helper.ProductOrderMapper;
import com.elogix.api.product_order.infrastructure.repository.ProductOrderData;
import com.elogix.api.product_order.infrastructure.repository.ProductOrderDataJpaRepository;
import com.elogix.api.product_order.infrastructure.repository.ProductOrderGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.RequestUtils;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.shared.infraestructure.notification.NotificationService;
import com.elogix.api.users.domain.usecase.UserBasicUseCase;

import jakarta.persistence.EntityManager;

@Configuration
public class ProductOrderUseCaseConfig {
    public static final String DELETED_FILTER = "deletedProductOrderFilter";

    @Bean
    public ProductOrderGatewayImpl productOrderGatewayImpl(
            ProductOrderDataJpaRepository repository,
            ProductOrderMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            NotificationService notificationService,
            StatusUseCase statusUseCase,
            CustomerUseCase customerUseCase,
            UserBasicUseCase userUseCase,
            ProductUseCase productUseCase,
            CustomerDataJpaRepository customerRepo,
            DeliveryZoneBasicUseCase deliveryZoneUseCase,
            BranchOfficeUseCase officeUseCase,
            MetricUnitUseCase metricUnitUseCase,
            MeasureDetailUseCase measureDetailUseCase,
            ProductBasicUseCase productBasicUseCase,
            RequestUtils requestUtils) {

        GenericBasicConfig<ProductOrder, ProductOrderData, ProductOrderDataJpaRepository, ProductOrderMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<ProductOrder, ProductOrderData, ProductOrderDataJpaRepository, ProductOrderMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        GenericStatusConfig<ProductOrder, ProductOrderData, ProductOrderDataJpaRepository, ProductOrderMapper> statusConfig = new GenericStatusConfig<>(
                genericConfig, statusUseCase);

        GenericProductionConfig<ProductOrder, ProductOrderData, ProductOrderDataJpaRepository, ProductOrderMapper> productionConfig = new GenericProductionConfig<>(
                statusConfig, notificationService);

        ProductOrderConfig<ProductOrder, ProductOrderData, ProductOrderDataJpaRepository, ProductOrderMapper> productOrderConfig = new ProductOrderConfig<>(
                productionConfig, productUseCase);

        return new ProductOrderGatewayImpl(productOrderConfig);
    }

    private GenericBasicConfig<ProductOrder, ProductOrderData, ProductOrderDataJpaRepository, ProductOrderMapper> createBasicConfig(
            ProductOrderDataJpaRepository repository,
            ProductOrderMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public ProductOrderUseCase productOrderUseCase(ProductOrderGateway gateway) {
        return new ProductOrderUseCase(gateway);
    }
}
