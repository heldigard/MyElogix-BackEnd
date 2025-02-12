package com.elogix.api.customers.domain.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.elogix.api.generics.domain.model.GenericNamedBasic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for
 * {@link com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer_basic.CustomerBasicData}
 */

@Getter
@Setter
@SuperBuilder
public class CustomerBasic extends GenericNamedBasic {
    private String documentNumber;
    private String email;
    private String phone;
    private Membership membership;

    @Builder.Default
    private Long hits = 0L;

    @Builder.Default
    @JsonProperty(value = "isActive", defaultValue = "true")
    private boolean isActive = true;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CustomerBasic that))
            return false;
        if (!super.equals(o))
            return false;
        return isActive == that.isActive &&
                Objects.equals(documentNumber, that.documentNumber) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(membership, that.membership) &&
                Objects.equals(hits, that.hits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                documentNumber,
                email,
                phone,
                membership,
                hits,
                isActive);
    }

    public CustomerBasic() {
        super();
    }
}
