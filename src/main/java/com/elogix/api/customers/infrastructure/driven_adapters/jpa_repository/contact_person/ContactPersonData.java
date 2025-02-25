package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.elogix.api.customers.application.config.ContactPersonUseCaseConfig;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeData;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedData;

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
@FilterDef(name = ContactPersonUseCaseConfig.DELETED_FILTER, parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = ContactPersonUseCaseConfig.DELETED_FILTER, condition = "is_deleted = :isDeleted")
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
        if (!(o instanceof ContactPersonData that))
            return false;
        if (!super.equals(o))
            return false;
        return Objects.equals(mobileNumberPrimary, that.mobileNumberPrimary) &&
                Objects.equals(mobileNumberSecondary, that.mobileNumberSecondary) &&
                Objects.equals(branchOfficeList, that.branchOfficeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mobileNumberPrimary, mobileNumberSecondary, branchOfficeList);
    }

    public ContactPersonData() {
        super();
    }
}
