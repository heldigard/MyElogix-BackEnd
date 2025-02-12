package com.elogix.api.delivery_orders.domain.model;

import java.util.Objects;

import com.elogix.api.generics.domain.model.GenericBasicEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status.StatusData}
 */

@Getter
@Setter
@SuperBuilder
public class Status extends GenericBasicEntity {
    private EStatus name;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Status status = (Status) o;
        return name == status.name && Objects.equals(description, status.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description);
    }

    public Status() {
        super();
    }
}
