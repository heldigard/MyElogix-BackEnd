package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office_basic;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.elogix.api.generics.infrastructure.repository.GenericBasic.GenericBasicEntityData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "branch_offices")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE branch_offices SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedBranchOfficeBasicFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedBranchOfficeBasicFilter", condition = "is_deleted = :isDeleted")
public class BranchOfficeBasicData extends GenericBasicEntityData {
    @Column(name = "customer_id")
    private Long customerId;

    @Column(nullable = false)
    private String address;

    public BranchOfficeBasicData() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BranchOfficeBasicData that = (BranchOfficeBasicData) o;
        return Objects.equals(customerId, that.customerId) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, address);
    }
}
