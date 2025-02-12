package com.elogix.api.customers.domain.model;

import com.elogix.api.generics.domain.model.GenericNamedBasic;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for
 * {@link com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityData}
 */

@Getter
@Setter
@SuperBuilder
public class CityBasic extends GenericNamedBasic {
    public CityBasic() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CityBasic that))
            return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
