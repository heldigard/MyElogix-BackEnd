package com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "metric_unit")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE metric_unit SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedMetricUnitFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedMetricUnitFilter", condition = "is_deleted = :isDeleted")
public class MetricUnitData extends GenericNamedData {
    @Column(name = "name", unique = true, length = 3)
    private String name;

    public MetricUnitData() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        MetricUnitData that = (MetricUnitData) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
