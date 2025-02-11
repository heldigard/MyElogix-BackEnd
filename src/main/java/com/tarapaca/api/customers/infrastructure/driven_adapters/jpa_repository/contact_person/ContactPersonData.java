package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeData;
import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedData;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
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
@FilterDef(name = "deletedContactPersonFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedContactPersonFilter", condition = "is_deleted = :isDeleted")
public class ContactPersonData extends GenericNamedData {
    @Column(length = 15)
    private String mobileNumberPrimary;

    @Column(length = 15)
    private String mobileNumberSecondary;

    @OneToMany(mappedBy = "contactPerson", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<BranchOfficeData> branchOfficeList = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        ContactPersonData that = (ContactPersonData) o;

        if (mobileNumberPrimary != null ? !mobileNumberPrimary.equals(that.mobileNumberPrimary)
                : that.mobileNumberPrimary != null)
            return false;
        if (mobileNumberSecondary != null ? !mobileNumberSecondary.equals(that.mobileNumberSecondary)
                : that.mobileNumberSecondary != null)
            return false;
        return branchOfficeList != null ? branchOfficeList.equals(that.branchOfficeList)
                : that.branchOfficeList == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mobileNumberPrimary != null ? mobileNumberPrimary.hashCode() : 0);
        result = 31 * result + (mobileNumberSecondary != null ? mobileNumberSecondary.hashCode() : 0);
        result = 31 * result + (branchOfficeList != null ? branchOfficeList.hashCode() : 0);
        return result;
    }

    public ContactPersonData() {
        super();
    }
}
