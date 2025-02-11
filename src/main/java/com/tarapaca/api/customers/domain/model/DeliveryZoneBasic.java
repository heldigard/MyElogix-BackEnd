package com.tarapaca.api.customers.domain.model;

import com.tarapaca.api.generics.domain.model.GenericNamedBasic;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicData}
 */

@Getter
@Setter
@SuperBuilder
public class DeliveryZoneBasic extends GenericNamedBasic {
    public DeliveryZoneBasic() {
        super();
    }
}
