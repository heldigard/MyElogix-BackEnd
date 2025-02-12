package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer_basic;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipData;
import com.elogix.api.generics.infrastructure.repository.GenericNamedBasic.GenericNamedBasicData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@FilterDef(name = "deletedCustomerBasicFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedCustomerBasicFilter", condition = "is_deleted = :isDeleted")
public class CustomerBasicData extends GenericNamedBasicData {
    @NotBlank
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank
    @Column(unique = true, name = "document_number", length = 15)
    private String documentNumber;

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
        if (!(o instanceof CustomerBasicData that))
            return false;
        if (!super.equals(o))
            return false;
        return isActive == that.isActive &&
                Objects.equals(name, that.name) &&
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
                name,
                documentNumber,
                email,
                phone,
                membership,
                hits,
                isActive);
    }

    public CustomerBasicData() {
        super();
    }
}
