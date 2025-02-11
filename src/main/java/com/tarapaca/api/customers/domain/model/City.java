package com.tarapaca.api.customers.domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.tarapaca.api.generics.domain.model.GenericNamed;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for
 * {@link com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityData}
 */

@Getter
@Setter
@SuperBuilder
public class City extends GenericNamed {
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
        City city = (City) o;
        return Objects.equals(neighborhoodList, city.neighborhoodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), neighborhoodList);
    }

    public City() {
        super();
    }
}
