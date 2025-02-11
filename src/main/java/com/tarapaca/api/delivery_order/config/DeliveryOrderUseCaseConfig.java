package com.tarapaca.api.delivery_order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.customers.domain.usecase.BranchOfficeUseCase;
import com.tarapaca.api.customers.domain.usecase.CustomerUseCase;
import com.tarapaca.api.customers.domain.usecase.DeliveryZoneBasicUseCase;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerDataJpaRepository;
import com.tarapaca.api.delivery_order.domain.gateway.DeliveryOrderGateway;
import com.tarapaca.api.delivery_order.domain.usecase.DeliveryOrderUseCase;
import com.tarapaca.api.delivery_order.infrastructure.helper.mapper.DeliveryOrderMapper;
import com.tarapaca.api.delivery_order.infrastructure.repository.delivery_order.DeliveryOrderDataJpaRepository;
import com.tarapaca.api.delivery_order.infrastructure.repository.delivery_order.DeliveryOrderGatewayImpl;
import com.tarapaca.api.delivery_orders.domain.usecase.MeasureDetailUseCase;
import com.tarapaca.api.delivery_orders.domain.usecase.MetricUnitUseCase;
import com.tarapaca.api.delivery_orders.domain.usecase.StatusUseCase;
import com.tarapaca.api.product.domain.usecase.ProductBasicUseCase;
import com.tarapaca.api.product.domain.usecase.ProductUseCase;
import com.tarapaca.api.shared.infraestructure.helpers.RequestUtils;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;
import com.tarapaca.api.users.domain.usecase.UserBasicUseCase;

import jakarta.persistence.EntityManager;

@Configuration
public class DeliveryOrderUseCaseConfig {

    @Bean
    public DeliveryOrderGatewayImpl deliveryOrderGatewayImpl(
            DeliveryOrderDataJpaRepository repository,
            DeliveryOrderMapper mapper,
            CustomerUseCase customerUseCase,
            UserBasicUseCase userUseCase,
            StatusUseCase statusUseCase,
            ProductUseCase productUseCase,
            EntityManager entityManager,
            CustomerDataJpaRepository customerRepo,
            DeliveryZoneBasicUseCase deliveryZoneUseCase,
            BranchOfficeUseCase officeUseCase,
            MetricUnitUseCase metricUnitUseCase,
            MeasureDetailUseCase measureDetailUseCase,
            ProductBasicUseCase productBasicUseCase,
            UpdateUtils updateUtils,
            RequestUtils requestUtils) {
        return new DeliveryOrderGatewayImpl(
                repository,
                mapper,
                customerUseCase,
                statusUseCase,
                productUseCase,
                entityManager,
                deliveryZoneUseCase,
                officeUseCase,
                metricUnitUseCase,
                measureDetailUseCase,
                productBasicUseCase,
                updateUtils,
                requestUtils,
                "deletedDeliveryOrderFilter");
    }

    @Bean
    public DeliveryOrderUseCase deliveryOrderUseCase(DeliveryOrderGateway gateway) {
        return new DeliveryOrderUseCase(gateway);
    }
}
