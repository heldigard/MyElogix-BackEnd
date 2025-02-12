package com.elogix.api.delivery_order.infrastructure.repository.delivery_order_basic;

import java.time.Instant;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer_basic.CustomerBasicData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicData;
import com.elogix.api.delivery_order.dto.CustomerOrdersSummaryDTO;
import com.elogix.api.generics.infrastructure.repository.GenericProduction.GenericProductionData;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user_basic.UserBasicData;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SqlResultSetMapping(name = "Mapping.CustomerOrdersSummaryDTO", classes = @ConstructorResult(targetClass = CustomerOrdersSummaryDTO.class, columns = {
    @ColumnResult(name = "customerId", type = Long.class),
    @ColumnResult(name = "deliveryOrdersCount", type = Long.class),
    @ColumnResult(name = "day", type = String.class),
    @ColumnResult(name = "customerName", type = String.class),
    @ColumnResult(name = "deliveryZoneName", type = String.class),
    @ColumnResult(name = "isBilled", type = Boolean.class),
    @ColumnResult(name = "deliveryOrderIds", type = String.class)
}))

@Entity
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Table(name = "delivery_orders")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE delivery_orders SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedDeliveryOrderBasicFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedDeliveryOrderBasicFilter", condition = "is_deleted = :isDeleted")
public class DeliveryOrderBasicData extends GenericProductionData {
  @Column(name = "billed_at", columnDefinition = "TIMESTAMP")
  private Instant billedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  @ToString.Exclude
  private CustomerBasicData customer;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_zone_id", referencedColumnName = "id")
  @ToString.Exclude
  private DeliveryZoneBasicData deliveryZone;

  @ManyToOne
  @JoinColumn(name = "billed_by_id")
  private UserBasicData billedBy;

  @Builder.Default
  @JsonProperty("isBilled")
  @Column(name = "is_billed", columnDefinition = "boolean DEFAULT false")
  private boolean isBilled = false;

  public DeliveryOrderBasicData() {
    super();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof DeliveryOrderBasicData that))
      return false;
    if (!super.equals(o))
      return false;
    return isBilled == that.isBilled
        && java.util.Objects.equals(billedAt, that.billedAt)
        && java.util.Objects.equals(customer, that.customer)
        && java.util.Objects.equals(deliveryZone, that.deliveryZone)
        && java.util.Objects.equals(billedBy, that.billedBy);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(super.hashCode(), billedAt, customer, deliveryZone, billedBy, isBilled);
  }

}
