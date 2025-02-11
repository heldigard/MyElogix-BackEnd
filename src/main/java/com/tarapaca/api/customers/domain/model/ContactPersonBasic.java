package com.tarapaca.api.customers.domain.model;

import java.util.Objects;

import com.tarapaca.api.generics.domain.model.GenericNamedBasic;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person.ContactPersonData}
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
        if (o == null || getClass() != o.getClass())
            return false;
        ContactPersonBasic that = (ContactPersonBasic) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getMobileNumberPrimary(), that.getMobileNumberPrimary()) &&
                Objects.equals(getMobileNumberSecondary(), that.getMobileNumberSecondary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMobileNumberPrimary(), getMobileNumberSecondary());
    }

    public ContactPersonBasic() {
        super();
    }
}
