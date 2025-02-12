package com.elogix.api.delivery_order.infrastructure.repository.delivery_order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicData;
import com.elogix.api.generics.infrastructure.repository.GenericProduction.GenericProductionData;
import com.elogix.api.product_order.infrastructure.repository.ProductOrderData;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user_basic.UserBasicData;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Table(name = "delivery_orders")
@Getter
@Setter
@SQLDelete(sql = "UPDATE delivery_orders SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedDeliveryOrderFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedDeliveryOrderFilter", condition = "is_deleted = :isDeleted")
public class DeliveryOrderData extends GenericProductionData {

    @Column(name = "billed_at", columnDefinition = "TIMESTAMP")
    private Instant billedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ToString.Exclude
    private CustomerData customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "delivery_zone_id", referencedColumnName = "id")
    @ToString.Exclude
    private DeliveryZoneBasicData deliveryZone;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "branch_office_id", referencedColumnName = "id")
    @ToString.Exclude
    private BranchOfficeData branchOffice;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_order_id")
    @OrderBy("id ASC")
    @ToString.Exclude
    private List<ProductOrderData> productOrders;

    @Basic
    private String generalObservations;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal totalPrice = new BigDecimal(0);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billed_by_id")
    private UserBasicData billedBy;

    @Builder.Default
    @JsonProperty("isBilled")
    @Column(name = "is_billed", columnDefinition = "boolean DEFAULT false")
    private boolean isBilled = false;

    public DeliveryOrderData() {
        super();
    }
}
