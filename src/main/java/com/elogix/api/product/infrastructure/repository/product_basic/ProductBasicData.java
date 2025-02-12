package com.elogix.api.product.infrastructure.repository.product_basic;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status.StatusData;
import com.elogix.api.generics.infrastructure.repository.GenericBasic.GenericBasicEntityData;
import com.elogix.api.product.infrastructure.repository.product_type.ProductTypeData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "products")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedProductBasicFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedProductBasicFilter", condition = "is_deleted = :isDeleted")
public class ProductBasicData extends GenericBasicEntityData {
    @NotBlank
    @Column(name = "reference", length = 50, unique = true, nullable = false)
    private String reference;

    @Column(length = 150)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ToString.Exclude
    private StatusData status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @ToString.Exclude
    private ProductTypeData type;

    @Builder.Default
    @Column(columnDefinition = "bigint default 0")
    private Long hits = 0L;

    @Builder.Default
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive = true;

    @Builder.Default
    @Column(name = "is_low_stock", columnDefinition = "boolean DEFAULT false")
    private boolean isLowStock = false;

    public ProductBasicData() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductBasicData that))
            return false;
        if (!super.equals(o))
            return false;
        return reference.equals(that.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), reference);
    }
}
