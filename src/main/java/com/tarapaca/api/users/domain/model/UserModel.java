package com.tarapaca.api.users.domain.model;

import java.util.Set;

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
@AllArgsConstructor
@NoArgsConstructor
public class UserModel extends GenericEntity {
    private String firstName;
    private String lastName;
    private transient Office office;
    private String department;
    private String email;
    private String phone;
    private String password;
    private String username;
    private String avatar;

    @Builder.Default
    @JsonProperty("isActive")
    private boolean isActive = true;

    @Builder.Default
    @JsonProperty("isLocked")
    private boolean isLocked = false;

    private Set<RoleModel> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        UserModel userModel = (UserModel) o;
        return isActive == userModel.isActive &&
                isLocked == userModel.isLocked &&
                java.util.Objects.equals(firstName, userModel.firstName) &&
                java.util.Objects.equals(lastName, userModel.lastName) &&
                java.util.Objects.equals(office, userModel.office) &&
                java.util.Objects.equals(department, userModel.department) &&
                java.util.Objects.equals(email, userModel.email) &&
                java.util.Objects.equals(phone, userModel.phone) &&
                java.util.Objects.equals(password, userModel.password) &&
                java.util.Objects.equals(username, userModel.username) &&
                java.util.Objects.equals(avatar, userModel.avatar) &&
                java.util.Objects.equals(roles, userModel.roles);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), firstName, lastName, office, department, email, phone,
                password, username, avatar, isActive, isLocked, roles);
    }
}
