package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.tarapaca.api.generics.infrastructure.repository.GenericNamedBasic.GenericNamedBasicData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "delivery_zones")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE delivery_zones SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedDeliveryZoneBasicFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedDeliveryZoneBasicFilter", condition = "is_deleted = :isDeleted")
public class DeliveryZoneBasicData extends GenericNamedBasicData {
    @Column(unique = true, length = 50, nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DeliveryZoneBasicData that = (DeliveryZoneBasicData) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public DeliveryZoneBasicData() {
        super();
    }
}
