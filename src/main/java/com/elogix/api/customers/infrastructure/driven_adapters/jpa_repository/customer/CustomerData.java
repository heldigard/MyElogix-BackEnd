package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipData;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedData;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Table(name = "customers", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "document_number" }) })
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE customers SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedCustomerFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedCustomerFilter", condition = "is_deleted = :isDeleted")
public class CustomerData extends GenericNamedData {
    @NotBlank
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank
    @Column(unique = true, name = "document_number", length = 15)
    private String documentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", referencedColumnName = "id")
    @ToString.Exclude
    private DocumentTypeData documentType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @OrderBy("id ASC")
    @ToString.Exclude
    @Builder.Default
    private Set<BranchOfficeData> branchOfficeList = new HashSet<>();

    @Email
    @Column(length = 100)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id", referencedColumnName = "id")
    @ToString.Exclude
    private MembershipData membership;

    @Builder.Default
    @Column(columnDefinition = "bigint default 0")
    private Long hits = 0L;

    @Builder.Default
    @JsonProperty(value = "isActive", defaultValue = "true")
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive = true;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CustomerData that))
            return false;
        if (!super.equals(o))
            return false;
        return isActive == that.isActive &&
                Objects.equals(name, that.name) &&
                Objects.equals(documentNumber, that.documentNumber) &&
                Objects.equals(documentType, that.documentType) &&
                Objects.equals(branchOfficeList, that.branchOfficeList) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(membership, that.membership) &&
                Objects.equals(hits, that.hits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                name,
                documentNumber,
                documentType,
                branchOfficeList,
                email,
                phone,
                membership,
                hits,
                isActive);
    }

    public CustomerData() {
        super();
        this.branchOfficeList = new HashSet<>();
        this.hits = 0L;
        this.isActive = true;
    }
}
