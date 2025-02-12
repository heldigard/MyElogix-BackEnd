package com.elogix.api.customers.domain.model;

import java.util.Objects;

import com.elogix.api.generics.domain.model.GenericBasicEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class BranchOfficeBasic extends GenericBasicEntity {
    private Long customerId;
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        BranchOfficeBasic that = (BranchOfficeBasic) o;
        return Objects.equals(customerId, that.customerId) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customerId, address);
    }

    public BranchOfficeBasic() {
        super();
    }
}
