package com.elogix.api.product_order.domain.model;

import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.elogix.api.delivery_orders.domain.model.MeasureDetail;
import com.elogix.api.delivery_orders.domain.model.MetricUnit;
import com.elogix.api.generics.domain.model.GenericProduction;
import com.elogix.api.product.domain.model.ProductBasic;
import com.elogix.api.product_order.infrastructure.repository.ProductOrderData;
import com.elogix.api.shared.application.config.CustomDoubleDeserializer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link ProductOrderData}
 */

@Getter
@Setter
@SuperBuilder
public class ProductOrder extends GenericProduction {
    private ProductBasic product;

    @Builder.Default
    private Long amount = 0L;

    @JsonDeserialize(using = CustomDoubleDeserializer.class)
    @Builder.Default
    private Double measure1 = 0.0d;

    @JsonDeserialize(using = CustomDoubleDeserializer.class)
    @Builder.Default
    private Double measure2 = 0.0d;

    private MetricUnit metricUnit;
    private MeasureDetail measureDetail;
    private String observation;
    private Long deliveryOrderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrder that = (ProductOrder) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(measure1, that.measure1) &&
                Objects.equals(measure2, that.measure2) &&
                Objects.equals(metricUnit, that.metricUnit) &&
                Objects.equals(measureDetail, that.measureDetail) &&
                Objects.equals(observation, that.observation) &&
                Objects.equals(deliveryOrderId, that.deliveryOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, amount, measure1, measure2, metricUnit, measureDetail, observation,
                deliveryOrderId);
    }

    public ProductOrder() {
        super();
    }
}
