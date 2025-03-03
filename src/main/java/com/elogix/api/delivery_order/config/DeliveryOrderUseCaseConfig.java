package com.elogix.api.delivery_order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.usecase.BranchOfficeUseCase;
import com.elogix.api.customers.domain.usecase.CustomerUseCase;
import com.elogix.api.customers.domain.usecase.DeliveryZoneBasicUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerDataJpaRepository;
import com.elogix.api.delivery_order.domain.gateway.DeliveryOrderGateway;
import com.elogix.api.delivery_order.domain.model.DeliveryOrder;
import com.elogix.api.delivery_order.domain.usecase.DeliveryOrderUseCase;
import com.elogix.api.delivery_order.infrastructure.helper.mapper.DeliveryOrderMapper;
import com.elogix.api.delivery_order.infrastructure.repository.delivery_order.DeliveryOrderData;
import com.elogix.api.delivery_order.infrastructure.repository.delivery_order.DeliveryOrderDataJpaRepository;
import com.elogix.api.delivery_order.infrastructure.repository.delivery_order.DeliveryOrderGatewayImpl;
import com.elogix.api.delivery_orders.domain.usecase.MeasureDetailUseCase;
import com.elogix.api.delivery_orders.domain.usecase.MetricUnitUseCase;
import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.delivery_orders.infrastructure.helpers.mappers.StatusMapper;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.config.GenericProductionConfig;
import com.elogix.api.generics.config.GenericStatusConfig;
import com.elogix.api.product.domain.usecase.ProductBasicUseCase;
import com.elogix.api.product.domain.usecase.ProductUseCase;
import com.elogix.api.product_order.config.ProductOrderConfig;
import com.elogix.api.shared.infraestructure.helpers.RequestUtils;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.shared.infraestructure.notification.NotificationService;
import com.elogix.api.users.domain.usecase.UserBasicUseCase;

import jakarta.persistence.EntityManager;

@Configuration
public class DeliveryOrderUseCaseConfig {
        public static final String DELETED_FILTER = "deletedDeliveryOrderFilter";

        @Bean
        public DeliveryOrderGatewayImpl deliveryOrderGatewayImpl(
                        DeliveryOrderDataJpaRepository repository,
                        DeliveryOrderMapper mapper,
                        EntityManager entityManager,
                        UpdateUtils updateUtils,
                        NotificationService notificationService,
                        StatusUseCase statusUseCase,
                        StatusMapper statusMapper,
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

                GenericBasicConfig<DeliveryOrder, DeliveryOrderData, DeliveryOrderDataJpaRepository, DeliveryOrderMapper> basicConfig = createBasicConfig(
                                repository, mapper, entityManager);

                GenericConfig<DeliveryOrder, DeliveryOrderData, DeliveryOrderDataJpaRepository, DeliveryOrderMapper> genericConfig = new GenericConfig<>(
                                basicConfig, updateUtils);

                GenericStatusConfig<DeliveryOrder, DeliveryOrderData, DeliveryOrderDataJpaRepository, DeliveryOrderMapper> statusConfig = new GenericStatusConfig<>(
                                genericConfig, statusUseCase, statusMapper);

                GenericProductionConfig<DeliveryOrder, DeliveryOrderData, DeliveryOrderDataJpaRepository, DeliveryOrderMapper> productionConfig = new GenericProductionConfig<>(
                                statusConfig, notificationService);

                ProductOrderConfig<DeliveryOrder, DeliveryOrderData, DeliveryOrderDataJpaRepository, DeliveryOrderMapper> productOrderConfig = new ProductOrderConfig<>(
                                productionConfig, productUseCase);

                return new DeliveryOrderGatewayImpl(
                                productOrderConfig,
                                customerUseCase,
                                deliveryZoneUseCase,
                                officeUseCase,
                                metricUnitUseCase,
                                measureDetailUseCase,
                                productBasicUseCase,
                                requestUtils);
        }

        private GenericBasicConfig<DeliveryOrder, DeliveryOrderData, DeliveryOrderDataJpaRepository, DeliveryOrderMapper> createBasicConfig(
                        DeliveryOrderDataJpaRepository repository,
                        DeliveryOrderMapper mapper,
                        EntityManager entityManager) {
                return new GenericBasicConfig<>(
                                repository,
                                mapper,
                                entityManager,
                                DELETED_FILTER);
        }

        @Bean
        public DeliveryOrderUseCase deliveryOrderUseCase(DeliveryOrderGateway gateway) {
                return new DeliveryOrderUseCase(gateway);
        }
}
