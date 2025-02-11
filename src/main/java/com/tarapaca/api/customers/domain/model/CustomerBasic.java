package com.tarapaca.api.customers.domain.model;

import java.util.Objects;

import com.tarapaca.api.generics.domain.model.GenericNamedBasic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for
 * {@link com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.customer_basic.CustomerBasicData}
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
    private boolean isActive = true;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        CustomerBasic that = (CustomerBasic) o;
        return isActive == that.isActive &&
                Objects.equals(documentNumber, that.documentNumber) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                membership.equals(that.membership) &&
                Objects.equals(hits, that.hits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), documentNumber, email, phone, membership.hashCode(), hits, isActive);
    }

    public CustomerBasic() {
        super();
    }
}
