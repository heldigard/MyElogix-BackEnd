package com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.role;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericEntityData;
import com.elogix.api.users.application.config.RoleUseCaseConfig;
import com.elogix.api.users.domain.model.ERole;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Table(name = "roles")
@AllArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE roles SET is_deleted = true WHERE id=?")
@FilterDef(name = RoleUseCaseConfig.DELETED_FILTER, parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = RoleUseCaseConfig.DELETED_FILTER, condition = "is_deleted = :isDeleted")
public class RoleData extends GenericEntityData {
    @Enumerated(EnumType.STRING)
    @Column(name = "name", unique = true, nullable = false)
    private ERole name;

    @Basic
    private String description;

    @Builder.Default
    @JsonProperty("isActive")
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive = true;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RoleData))
            return false;
        if (!super.equals(o))
            return false;
        RoleData roleData = (RoleData) o;
        return isActive == roleData.isActive &&
                name == roleData.name &&
                Objects.equals(description, roleData.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, isActive);
    }

    public RoleData() {
        super();
    }
}
