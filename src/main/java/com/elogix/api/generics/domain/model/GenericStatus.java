package com.elogix.api.generics.domain.model;

import java.util.Objects;

import com.elogix.api.delivery_orders.domain.model.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class GenericStatus extends GenericEntity {
    private Status status;

    protected GenericStatus() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenericStatus))
            return false;
        if (!super.equals(o))
            return false;
        GenericStatus that = (GenericStatus) o;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", status=" + status;
    }
}
