package com.elogix.api.generics.infrastructure.repository.GenericNamed;

import java.util.Objects;

import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericEntityData;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Extended entity class that adds name field to audited entities
 */
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
public abstract class GenericNamedData extends GenericEntityData {

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @Column(length = 100)
    private String name;

    protected GenericNamedData() {
        super();
    }

    protected GenericNamedData(String name) {
        super();
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
        GenericNamedData that = (GenericNamedData) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(id=" + getId() + ", name=" + name + ")";
    }
}
