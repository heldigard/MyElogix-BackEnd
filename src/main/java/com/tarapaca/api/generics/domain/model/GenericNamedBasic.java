package com.tarapaca.api.generics.domain.model;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Extended domain entity class that adds name field to basic entities
 */
@Getter
@Setter
@SuperBuilder
public abstract class GenericNamedBasic extends GenericBasicEntity {
    private String name;

    protected GenericNamedBasic() {
        super();
    }

    protected GenericNamedBasic(Long id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        GenericNamedBasic that = (GenericNamedBasic) o;
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
