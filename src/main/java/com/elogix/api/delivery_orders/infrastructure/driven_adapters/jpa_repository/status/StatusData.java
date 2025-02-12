package com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.generics.infrastructure.repository.GenericBasic.GenericBasicEntityData;

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
@Table(name = "statuses")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE statuses SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedStatusFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedStatusFilter", condition = "is_deleted = :isDeleted")
public class StatusData extends GenericBasicEntityData {
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private EStatus name;

    @Column(length = 50)
    private String description;

    public StatusData() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof StatusData that))
            return false;
        if (!super.equals(o))
            return false;
        return name == that.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
