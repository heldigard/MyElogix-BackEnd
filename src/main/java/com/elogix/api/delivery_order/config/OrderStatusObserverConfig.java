package com.elogix.api.delivery_order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.elogix.api.delivery_order.infrastructure.repository.delivery_order.DeliveryOrderGatewayImpl;
import com.elogix.api.product_order.infrastructure.repository.ProductOrderGatewayImpl;

@Configuration
public class OrderStatusObserverConfig {

    @Bean
    @Primary
    public boolean wireStatusObservers(
            ProductOrderGatewayImpl productOrderGateway,
            DeliveryOrderGatewayImpl deliveryOrderGateway) {
        productOrderGateway.addStatusObserver(deliveryOrderGateway);
        return true;
    }
}
