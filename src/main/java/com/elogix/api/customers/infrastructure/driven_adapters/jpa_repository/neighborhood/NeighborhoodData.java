package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicData;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "neighborhoods", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "city_id" }) })
@Getter
@Setter
@SQLDelete(sql = "UPDATE neighborhoods SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedNeighborhoodFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedNeighborhoodFilter", condition = "is_deleted = :isDeleted")
public class NeighborhoodData extends GenericNamedData {
    @NotBlank
    @Column(length = 50, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private CityBasicData city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_zone_id", nullable = false)
    private DeliveryZoneBasicData deliveryZone;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NeighborhoodData that))
            return false;
        if (!super.equals(o))
            return false;
        return Objects.equals(name, that.name) &&
                Objects.equals(city, that.city) &&
                Objects.equals(deliveryZone, that.deliveryZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, city, deliveryZone);
    }

    public NeighborhoodData() {
        super();
    }
}
