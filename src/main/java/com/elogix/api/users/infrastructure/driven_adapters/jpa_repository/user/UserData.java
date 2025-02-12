package com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericEntityData;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.office.OfficeData;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.role.RoleData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedUserFilter", condition = "is_deleted = :isDeleted")
public class UserData extends GenericEntityData {
    @NotBlank
    @Column(length = 100, nullable = false)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    @ToString.Exclude
    private OfficeData office;

    @Email
    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 100)
    private String phone;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(length = 100, unique = true, nullable = false)
    private String username;

    @Column(length = 100)
    private String avatar;

    @Builder.Default
    @JsonProperty("isActive")
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive = true;

    @Builder.Default
    @JsonProperty("isLocked")
    @Column(name = "is_locked", columnDefinition = "boolean DEFAULT false")
    private boolean isLocked = false;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleData.class)
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id") })
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @ToString.Exclude
    private Set<RoleData> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserData))
            return false;
        if (!super.equals(o))
            return false;
        UserData userData = (UserData) o;
        return username.equals(userData.username) && email.equals(userData.email);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), username, email);
    }
}
