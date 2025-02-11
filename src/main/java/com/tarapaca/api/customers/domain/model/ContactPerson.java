package com.tarapaca.api.customers.domain.model;

import java.util.HashSet;
import java.util.Set;

import com.tarapaca.api.generics.domain.model.GenericNamed;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person.ContactPersonData}
 */

@Getter
@Setter
@SuperBuilder
public class ContactPerson extends GenericNamed {
    private String mobileNumberPrimary;
    private String mobileNumberSecondary;

    @Builder.Default
    private Set<Long> branchOfficeIdList = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        ContactPerson that = (ContactPerson) o;

        if (mobileNumberPrimary != null ? !mobileNumberPrimary.equals(that.mobileNumberPrimary)
                : that.mobileNumberPrimary != null)
            return false;
        if (mobileNumberSecondary != null ? !mobileNumberSecondary.equals(that.mobileNumberSecondary)
                : that.mobileNumberSecondary != null)
            return false;
        return branchOfficeIdList != null ? branchOfficeIdList.equals(that.branchOfficeIdList)
                : that.branchOfficeIdList == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mobileNumberPrimary != null ? mobileNumberPrimary.hashCode() : 0);
        result = 31 * result + (mobileNumberSecondary != null ? mobileNumberSecondary.hashCode() : 0);
        result = 31 * result + (branchOfficeIdList != null ? branchOfficeIdList.hashCode() : 0);
        return result;
    }

    public ContactPerson() {
        super();
    }
}
