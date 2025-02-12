package com.elogix.api.customers.domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.elogix.api.generics.domain.model.GenericNamed;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for
 * {@link com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone.DeliveryZoneData}
 */

@Getter
@Setter
@SuperBuilder
public class DeliveryZone extends GenericNamed {
    @Builder.Default
    private Set<Neighborhood> neighborhoodList = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        DeliveryZone that = (DeliveryZone) o;
        return Objects.equals(neighborhoodList, that.neighborhoodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), neighborhoodList);
    }

    public DeliveryZone() {
        super();
    }
}
