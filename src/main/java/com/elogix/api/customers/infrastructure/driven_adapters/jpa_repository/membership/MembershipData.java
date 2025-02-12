package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.membership;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.elogix.api.customers.application.config.EMembershipDeserializer;
import com.elogix.api.customers.domain.model.EMembership;
import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericEntityData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "memberships")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE memberships SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedMembershipFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedMembershipFilter", condition = "is_deleted = :isDeleted")
public class MembershipData extends GenericEntityData {
    @Enumerated(EnumType.STRING)
    @JsonDeserialize(using = EMembershipDeserializer.class)
    private EMembership name;

    @Column(length = 50)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MembershipData that))
            return false;
        if (!super.equals(o))
            return false;
        return name == that.name &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description);
    }

    public MembershipData() {
        super();
    }
}
