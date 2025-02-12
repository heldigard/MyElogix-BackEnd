package com.elogix.api.users.domain.model;

import com.elogix.api.generics.domain.model.GenericNamed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Office extends GenericNamed {
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Office office = (Office) o;
        return java.util.Objects.equals(address, office.address);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), address);
    }
}
