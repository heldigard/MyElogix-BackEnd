package com.tarapaca.api.users.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tarapaca.api.generics.domain.model.GenericEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoleModel extends GenericEntity {
    private ERole name;
    private String description;

    @Builder.Default
    @JsonProperty("isActive")
    private boolean isActive = true;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        RoleModel roleModel = (RoleModel) o;
        return isActive == roleModel.isActive &&
                name == roleModel.name &&
                java.util.Objects.equals(description, roleModel.description);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), name, description, isActive);
    }
}
