package com.tarapaca.api.customers.domain.model;

import java.util.Objects;

import com.tarapaca.api.generics.domain.model.GenericNamed;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodData}
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
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Neighborhood that = (Neighborhood) o;
        return city.equals(that.city) &&
                deliveryZone.equals(that.deliveryZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), city.hashCode(), deliveryZone.hashCode());
    }

    public Neighborhood() {
        super();
    }
}
