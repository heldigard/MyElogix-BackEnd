package com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user_basic;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.elogix.api.generics.infrastructure.repository.GenericBasic.GenericBasicEntityData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedUserBasicFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedUserBasicFilter", condition = "is_deleted = :isDeleted")
public class UserBasicData extends GenericBasicEntityData {
    @NotBlank
    @Column(length = 100, nullable = false)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @NotBlank
    @Column(length = 100, unique = true, nullable = false)
    private String username;

    @Builder.Default
    @JsonProperty("isActive")
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive = true;

    @Builder.Default
    @JsonProperty("isLocked")
    @Column(name = "is_locked", columnDefinition = "boolean DEFAULT false")
    private boolean isLocked = false;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        UserBasicData that = (UserBasicData) o;
        return Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(isActive(), that.isActive()) &&
                Objects.equals(isLocked(), that.isLocked());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getFirstName(),
                getLastName(),
                getUsername(),
                isActive(),
                isLocked());
    }
}
