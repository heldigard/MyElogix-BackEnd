package com.elogix.api.product_order.infrastructure.repository;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.measure_detail.MeasureDetailData;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit.MetricUnitData;
import com.elogix.api.generics.infrastructure.repository.GenericProduction.GenericProductionData;
import com.elogix.api.product.infrastructure.repository.product_basic.ProductBasicData;
import com.elogix.api.shared.application.config.CustomDoubleDeserializer;

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
@ToString
@SQLDelete(sql = "UPDATE product_orders SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedProductOrderFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedProductOrderFilter", condition = "is_deleted = :isDeleted")
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
}
