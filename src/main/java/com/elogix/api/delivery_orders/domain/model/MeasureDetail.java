package com.elogix.api.delivery_orders.domain.model;

import java.util.Objects;

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
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description);
    }

    @Override
    public String toString() {
        return "MeasureDetail{" +
                super.toString() +
                ", description='" + description + '\'' +
                '}';
    }
}
