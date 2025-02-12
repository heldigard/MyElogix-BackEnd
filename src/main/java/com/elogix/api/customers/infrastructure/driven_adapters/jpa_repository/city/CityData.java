package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

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
@Table(name = "cities")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE cities SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedCityFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedCityFilter", condition = "is_deleted = :isDeleted")
public class CityData extends GenericNamedData {
    @NotBlank
    @Column(unique = true, length = 25, nullable = false)
    private String name;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<NeighborhoodData> neighborhoodList = new HashSet<>();

    public CityData() {
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

        CityData cityData = (CityData) o;
        return name != null ? name.equals(cityData.name) : cityData.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
