package com.elogix.api.customers.domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.elogix.api.generics.domain.model.GenericNamed;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for
 * {@link com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerData}
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
    @JsonProperty(value = "isActive", defaultValue = "true")
    private boolean isActive = true;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Customer customer))
            return false;
        if (!super.equals(o))
            return false;
        return isActive == customer.isActive &&
                Objects.equals(documentNumber, customer.documentNumber) &&
                Objects.equals(documentType, customer.documentType) &&
                Objects.equals(branchOfficeList, customer.branchOfficeList) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(phone, customer.phone) &&
                Objects.equals(membership, customer.membership) &&
                Objects.equals(hits, customer.hits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), documentNumber, documentType,
                branchOfficeList, email, phone, membership, hits, isActive);
    }

    public Customer() {
        super();
    }

    @Override
    public String toString() {
        return "Customer{" +
                super.toString() +
                ", documentNumber='" + documentNumber + '\'' +
                ", branchOfficeList=[size=" + (branchOfficeList != null ? branchOfficeList.size() : 0) + "]" +
                ", membership=" + membership +
                ", hits=" + hits +
                ", isActive=" + isActive +
                '}';
    }
}
