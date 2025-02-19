package com.elogix.api.customers.domain.model;

import java.util.Objects;

import com.elogix.api.generics.domain.model.GenericEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class BranchOffice extends GenericEntity {
    private Long customerId;
    private String address;
    private ContactPersonBasic contactPerson;
    private CityBasic city;
    private Neighborhood neighborhood;
    private DeliveryZoneBasic deliveryZone;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        BranchOffice that = (BranchOffice) o;
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

    public BranchOffice() {
        super();
    }

    @Override
    public String toString() {
        return "BranchOffice{" +
                super.toString() +
                ", customerId=" + customerId +
                ", address='" + address + '\'' +
                ", contactPerson=" + contactPerson +
                ", city=" + city +
                ", neighborhood=" + neighborhood +
                ", deliveryZone=" + deliveryZone +
                '}';
    }

}
