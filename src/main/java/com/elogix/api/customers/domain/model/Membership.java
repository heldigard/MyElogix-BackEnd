package com.elogix.api.customers.domain.model;

import java.util.Objects;

import com.elogix.api.customers.application.config.EMembershipDeserializer;
import com.elogix.api.generics.domain.model.GenericEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for
 * {@link com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipData}
 */

@Getter
@Setter
@SuperBuilder
public class Membership extends GenericEntity {
    @JsonDeserialize(using = EMembershipDeserializer.class)
    private EMembership name;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Membership that))
            return false;
        if (!super.equals(o))
            return false;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description);
    }

    public Membership() {
        super();
    }

    @Override
    public String toString() {
        return "Membership{" +
                super.toString() +
                ", name=" + name +
                ", description='" + description + '\'' +
                '}';
    }
}
