package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person_basic.ContactPersonBasicData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodData;
import com.tarapaca.api.generics.infrastructure.repository.GenericEntity.GenericEntityData;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "branch_offices")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE branch_offices SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedBranchOfficeFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedBranchOfficeFilter", condition = "is_deleted = :isDeleted")
public class BranchOfficeData extends GenericEntityData {
    @Column(name = "customer_id")
    private Long customerId;

    @Column(nullable = false)
    private String address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_person_id", referencedColumnName = "id")
    private ContactPersonBasicData contactPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ToString.Exclude
    private CityBasicData city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "neighborhood_id", referencedColumnName = "id")
    @ToString.Exclude
    private NeighborhoodData neighborhood;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_zone_id", referencedColumnName = "id")
    @ToString.Exclude
    private DeliveryZoneBasicData deliveryZone;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        BranchOfficeData that = (BranchOfficeData) o;
        return Objects.equals(customerId, that.customerId) &&
                Objects.equals(address, that.address) &&
                Objects.equals(contactPerson, that.contactPerson) &&
                Objects.equals(city, that.city) &&
                Objects.equals(neighborhood, that.neighborhood) &&
                Objects.equals(deliveryZone, that.deliveryZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                customerId,
                address,
                Objects.nonNull(contactPerson) ? contactPerson.hashCode() : 0,
                Objects.nonNull(city) ? city.hashCode() : 0,
                Objects.nonNull(neighborhood) ? neighborhood.hashCode() : 0,
                Objects.nonNull(deliveryZone) ? deliveryZone.hashCode() : 0);
    }

    public BranchOfficeData() {
        super();
    }

}
