package com.tarapaca.api.customers.domain.model;

import java.util.HashSet;
import java.util.Set;

import com.tarapaca.api.generics.domain.model.GenericNamed;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerData}
 */

@Getter
@Setter
@SuperBuilder
public class Customer extends GenericNamed {
    private String documentNumber;
    private DocumentType documentType;

    @Builder.Default
    private Set<BranchOffice> branchOfficeList = new HashSet<>();

    private String email;
    private String phone;
    private Membership membership;

    @Builder.Default
    private Long hits = 0L;

    @Builder.Default
    private boolean isActive = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Customer customer = (Customer) o;

        if (isActive != customer.isActive) return false;
        if (documentNumber != null ? !documentNumber.equals(customer.documentNumber) : customer.documentNumber != null)
            return false;
        if (documentType != null ? !documentType.equals(customer.documentType) : customer.documentType != null)
            return false;
        if (branchOfficeList != null ? !branchOfficeList.equals(customer.branchOfficeList) : customer.branchOfficeList != null)
            return false;
        if (email != null ? !email.equals(customer.email) : customer.email != null) return false;
        if (phone != null ? !phone.equals(customer.phone) : customer.phone != null) return false;
        if (membership != null ? !membership.equals(customer.membership) : customer.membership != null) return false;
        return hits != null ? hits.equals(customer.hits) : customer.hits == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (documentNumber != null ? documentNumber.hashCode() : 0);
        result = 31 * result + (documentType != null ? documentType.hashCode() : 0);
        result = 31 * result + (branchOfficeList != null ? branchOfficeList.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (membership != null ? membership.hashCode() : 0);
        result = 31 * result + (hits != null ? hits.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    public Customer() {
        super();
    }
}
