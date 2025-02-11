package com.tarapaca.api.generics.domain.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Extended domain entity class that adds name field
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public abstract class GenericNamed extends GenericEntity {

    private String name;

    protected GenericNamed() {
        super();
    }

    protected GenericNamed(Long id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenericNamed))
            return false;
        if (!super.equals(o))
            return false;
        GenericNamed that = (GenericNamed) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return super.toString() + ", name=" + name;
    }
}
