package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.customer;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipData;
import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedData;

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
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive = true;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        CustomerData that = (CustomerData) o;

        if (isActive != that.isActive)
            return false;
        if (!name.equals(that.name))
            return false;
        if (!documentNumber.equals(that.documentNumber))
            return false;
        if (documentType != null ? !documentType.equals(that.documentType) : that.documentType != null)
            return false;
        if (branchOfficeList != null ? !branchOfficeList.equals(that.branchOfficeList) : that.branchOfficeList != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null)
            return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null)
            return false;
        if (membership != null ? !membership.equals(that.membership) : that.membership != null)
            return false;
        return hits != null ? hits.equals(that.hits) : that.hits == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + documentNumber.hashCode();
        result = 31 * result + (documentType != null ? documentType.hashCode() : 0);
        result = 31 * result + (branchOfficeList != null ? branchOfficeList.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (membership != null ? membership.hashCode() : 0);
        result = 31 * result + (hits != null ? hits.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    public CustomerData() {
        super();
        this.branchOfficeList = new HashSet<>();
        this.hits = 0L;
        this.isActive = true;
    }
}
