package com.tarapaca.api.users.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tarapaca.api.generics.domain.model.GenericBasicEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBasic extends GenericBasicEntity {
    private String firstName;
    private String lastName;
    private String username;

    @Builder.Default
    @JsonProperty("isActive")
    private boolean isActive = true;

    @Builder.Default
    @JsonProperty("isLocked")
    private boolean isLocked = false;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserBasic userBasic))
            return false;
        if (!super.equals(o))
            return false;
        return isActive == userBasic.isActive &&
                isLocked == userBasic.isLocked &&
                firstName.equals(userBasic.firstName) &&
                lastName.equals(userBasic.lastName) &&
                username.equals(userBasic.username);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), firstName, lastName, username, isActive, isLocked);
    }
}
