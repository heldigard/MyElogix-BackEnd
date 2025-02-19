package com.elogix.api.customers.domain.model;

import java.util.Objects;

import com.elogix.api.generics.domain.model.GenericNamedBasic;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for
 * {@link com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person.ContactPersonData}
 */

@Getter
@Setter
@SuperBuilder
public class ContactPersonBasic extends GenericNamedBasic {
    private String mobileNumberPrimary;
    private String mobileNumberSecondary;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ContactPersonBasic that))
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

    public ContactPersonBasic() {
        super();
    }

    @Override
    public String toString() {
        return "ContactPersonBasic{" +
                super.toString() +
                ", mobileNumberPrimary='" + mobileNumberPrimary + '\'' +
                ", mobileNumberSecondary='" + mobileNumberSecondary + '\'' +
                '}';
    }
}
