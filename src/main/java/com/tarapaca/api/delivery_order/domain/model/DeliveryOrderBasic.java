package com.tarapaca.api.delivery_order.domain.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tarapaca.api.customers.domain.model.CustomerBasic;
import com.tarapaca.api.customers.domain.model.DeliveryZoneBasic;
import com.tarapaca.api.generics.domain.model.GenericProduction;
import com.tarapaca.api.users.domain.model.UserBasic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class DeliveryOrderBasic extends GenericProduction {
    private CustomerBasic customer;
    private DeliveryZoneBasic deliveryZone;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Instant billedAt;
    private UserBasic billedBy;
    @Builder.Default
    @JsonProperty("isBilled")
    private boolean isBilled = false;

    public DeliveryOrderBasic() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DeliveryOrderBasic))
            return false;
        if (!super.equals(o))
            return false;
        DeliveryOrderBasic that = (DeliveryOrderBasic) o;
        return isBilled == that.isBilled &&
                java.util.Objects.equals(customer, that.customer) &&
                java.util.Objects.equals(deliveryZone, that.deliveryZone) &&
                java.util.Objects.equals(billedAt, that.billedAt) &&
                java.util.Objects.equals(billedBy, that.billedBy);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), customer, deliveryZone, billedAt, billedBy, isBilled);
    }
}
