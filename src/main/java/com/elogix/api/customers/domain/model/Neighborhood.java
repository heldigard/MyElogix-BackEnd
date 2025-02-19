package com.elogix.api.customers.domain.model;

import java.util.Objects;

import com.elogix.api.generics.domain.model.GenericNamed;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for
 * {@link com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodData}
 */

@Getter
@Setter
@SuperBuilder
public class Neighborhood extends GenericNamed {
    private CityBasic city;
    private DeliveryZoneBasic deliveryZone;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Neighborhood that))
            return false;
        if (!super.equals(o))
            return false;
        return Objects.equals(city, that.city) &&
                Objects.equals(deliveryZone, that.deliveryZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), city, deliveryZone);
    }

    public Neighborhood() {
        super();
    }

    @Override
    public String toString() {
        return "Neighborhood{" +
                super.toString() +
                ", city=" + city +
                ", deliveryZone=" + deliveryZone +
                '}';
    }
}
