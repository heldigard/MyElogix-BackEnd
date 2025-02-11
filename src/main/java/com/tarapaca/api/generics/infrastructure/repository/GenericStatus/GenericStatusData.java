package com.tarapaca.api.generics.infrastructure.repository.GenericStatus;

import java.util.Objects;

import com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status.StatusData;
import com.tarapaca.api.generics.infrastructure.repository.GenericEntity.GenericEntityData;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Extended entity class that adds production status management
 */
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
public abstract class GenericStatusData extends GenericEntityData {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ToString.Exclude
    private StatusData status;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        GenericStatusData that = (GenericStatusData) o;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status);
    }

    protected GenericStatusData() {
        super();
    }
}
