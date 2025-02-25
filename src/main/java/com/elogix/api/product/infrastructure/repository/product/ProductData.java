package com.elogix.api.product.infrastructure.repository.product;

import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.elogix.api.generics.infrastructure.repository.GenericStatus.GenericStatusData;
import com.elogix.api.product.config.ProductUseCaseConfig;
import com.elogix.api.product.infrastructure.repository.product_type.ProductTypeData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Table(name = "products")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE id=?")
@FilterDef(name = ProductUseCaseConfig.DELETED_FILTER, parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = ProductUseCaseConfig.DELETED_FILTER, condition = "is_deleted = :isDeleted")
public class ProductData extends GenericStatusData {
    @NotBlank
    @Column(name = "reference", length = 50, unique = true, nullable = false)
    private String reference;

    @Column(length = 150)
    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductData that = (ProductData) o;
        return isActive == that.isActive &&
                isLowStock == that.isLowStock &&
                Objects.equals(reference, that.reference) &&
                Objects.equals(description, that.description) &&
                Objects.equals(type, that.type) &&
                Objects.equals(hits, that.hits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, description, type, hits, isActive, isLowStock);
    }

    public ProductData() {
        super();
    }
}
