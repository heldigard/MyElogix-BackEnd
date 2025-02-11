package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic;

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
@Table(name = "cities")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE cities SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedCityBasicFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedCityBasicFilter", condition = "is_deleted = :isDeleted")
public class CityBasicData extends GenericNamedBasicData {
    @Column(unique = true, length = 25, nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CityBasicData that = (CityBasicData) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public CityBasicData() {
        super();
    }
}
