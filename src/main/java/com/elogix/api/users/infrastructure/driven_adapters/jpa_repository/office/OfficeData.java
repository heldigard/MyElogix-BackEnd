package com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.office;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "offices")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE offices SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedOfficeFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedOfficeFilter", condition = "is_deleted = :isDeleted")
public class OfficeData extends GenericNamedData {
    @NotBlank
    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Column(length = 100)
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof OfficeData))
            return false;
        if (!super.equals(o))
            return false;
        OfficeData that = (OfficeData) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, address);
    }
}
