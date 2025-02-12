package com.elogix.api.delivery_orders.domain.model;

import com.elogix.api.generics.domain.model.GenericNamed;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for
 * {@link com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.measure_detail.MeasureDetailData}
 */

@Getter
@Setter
@SuperBuilder
public class MeasureDetail extends GenericNamed {
    private String description;

    public MeasureDetail() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        if (!super.equals(obj))
            return false;
        MeasureDetail that = (MeasureDetail) obj;
        return java.util.Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), description);
    }
}
