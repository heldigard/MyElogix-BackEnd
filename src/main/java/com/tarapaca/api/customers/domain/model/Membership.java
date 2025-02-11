package com.tarapaca.api.customers.domain.model;

import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tarapaca.api.customers.application.config.EMembershipDeserializer;
import com.tarapaca.api.generics.domain.model.GenericEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipData}
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
        if (o == null || getClass() != o.getClass())
            return false;
        Membership that = (Membership) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    public Membership() {
        super();
    }
}
