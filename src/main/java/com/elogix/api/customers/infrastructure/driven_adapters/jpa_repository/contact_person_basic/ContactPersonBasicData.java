package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person_basic;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.elogix.api.generics.infrastructure.repository.GenericNamedBasic.GenericNamedBasicData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "contact_people")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE contact_people SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedContactPersonBasicFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedContactPersonBasicFilter", condition = "is_deleted = :isDeleted")
public class ContactPersonBasicData extends GenericNamedBasicData {
    @Column(length = 15)
    private String mobileNumberPrimary;

    @Column(length = 15)
    private String mobileNumberSecondary;

    public ContactPersonBasicData() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ContactPersonBasicData that))
            return false;
        if (!super.equals(o))
            return false;
        return Objects.equals(mobileNumberPrimary, that.mobileNumberPrimary) &&
                Objects.equals(mobileNumberSecondary, that.mobileNumberSecondary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mobileNumberPrimary, mobileNumberSecondary);
    }
}
