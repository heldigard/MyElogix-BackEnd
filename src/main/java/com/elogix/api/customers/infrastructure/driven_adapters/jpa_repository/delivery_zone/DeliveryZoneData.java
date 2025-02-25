package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.elogix.api.customers.application.config.DeliveryZoneUseCaseConfig;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodData;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedData;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
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
@FilterDef(name = DeliveryZoneUseCaseConfig.DELETED_FILTER, parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = DeliveryZoneUseCaseConfig.DELETED_FILTER, condition = "is_deleted = :isDeleted")
public class DeliveryZoneData extends GenericNamedData {
    @NotBlank
    @Column(unique = true, length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "deliveryZone", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @Builder.Default
    private Set<NeighborhoodData> neighborhoodList = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DeliveryZoneData))
            return false;
        if (!super.equals(o))
            return false;
        DeliveryZoneData that = (DeliveryZoneData) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(neighborhoodList, that.neighborhoodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, neighborhoodList);
    }

    public DeliveryZoneData() {
        super();
    }
}
