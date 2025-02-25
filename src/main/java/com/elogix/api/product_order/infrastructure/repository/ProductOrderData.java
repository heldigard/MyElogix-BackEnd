package com.elogix.api.product_order.infrastructure.repository;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.measure_detail.MeasureDetailData;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit.MetricUnitData;
import com.elogix.api.generics.infrastructure.repository.GenericProduction.GenericProductionData;
import com.elogix.api.product.infrastructure.repository.product_basic.ProductBasicData;
import com.elogix.api.product_order.config.ProductOrderUseCaseConfig;
import com.elogix.api.shared.application.config.CustomDoubleDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Table(name = "product_orders")
@Getter
@Setter
@SQLDelete(sql = "UPDATE product_orders SET is_deleted = true WHERE id=?")
@FilterDef(name = ProductOrderUseCaseConfig.DELETED_FILTER, parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = ProductOrderUseCaseConfig.DELETED_FILTER, condition = "is_deleted = :isDeleted")
public class ProductOrderData extends GenericProductionData {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ToString.Exclude
    private ProductBasicData product;

    @Builder.Default
    @Column(columnDefinition = "bigint default 0")
    private Long amount = 0L;

    @JsonDeserialize(using = CustomDoubleDeserializer.class)
    @Builder.Default
    @Column(columnDefinition = "Decimal(10,2)")
    private Double measure1 = 0.0d;

    @JsonDeserialize(using = CustomDoubleDeserializer.class)
    @Builder.Default
    @Column(columnDefinition = "Decimal(10,2)")
    private Double measure2 = 0.0d;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metric_unit_id", referencedColumnName = "id")
    private MetricUnitData metricUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measure_detail_id", referencedColumnName = "id")
    @ToString.Exclude
    private MeasureDetailData measureDetail;

    @Basic
    private String observation;

    @Column(name = "delivery_order_id")
    private Long deliveryOrderId;

    public ProductOrderData() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ProductOrderData that))
            return false;
        if (!super.equals(o))
            return false;
        return Objects.equals(amount, that.amount) &&
                Objects.equals(measure1, that.measure1) &&
                Objects.equals(measure2, that.measure2) &&
                Objects.equals(observation, that.observation) &&
                Objects.equals(deliveryOrderId, that.deliveryOrderId) &&
                Objects.equals(product, that.product) &&
                Objects.equals(metricUnit, that.metricUnit) &&
                Objects.equals(measureDetail, that.measureDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                product,
                amount,
                measure1,
                measure2,
                metricUnit,
                measureDetail,
                observation,
                deliveryOrderId);
    }

    @Override
    public String toString() {
        return "ProductOrderData{" +
                super.toString() +
                ", product=" + (product != null ? product.getReference() : "null") +
                ", amount=" + amount +
                ", measure1=" + measure1 +
                ", measure2=" + measure2 +
                ", metricUnit=" + (metricUnit != null ? metricUnit.getId() : "null") +
                ", measureDetail=" + (measureDetail != null ? measureDetail.getId() : "null") +
                ", observation='" + observation + '\'' +
                ", deliveryOrderId=" + deliveryOrderId +
                '}';
    }
}
