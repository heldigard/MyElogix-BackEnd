package com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.measure_detail;

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
@Table(name = "measure_details")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE measure_details SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedMeasureDetailFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedMeasureDetailFilter", condition = "is_deleted = :isDeleted")
public class MeasureDetailData extends GenericNamedData {
    @Column(unique = true, length = 25, nullable = false)
    private String name;

    @Column(length = 50)
    private String description;

    public MeasureDetailData() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        if (!super.equals(obj))
            return false;
        MeasureDetailData that = (MeasureDetailData) obj;
        return name.equals(that.name) &&
                (description == null ? that.description == null : description.equals(that.description));
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
