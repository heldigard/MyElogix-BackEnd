package com.tarapaca.api.customers.domain.model;

import com.tarapaca.api.generics.domain.model.GenericNamedBasic;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityData}
 */

@Getter
@Setter
@SuperBuilder
public class CityBasic extends GenericNamedBasic {
    public CityBasic() {
        super();
    }
}
