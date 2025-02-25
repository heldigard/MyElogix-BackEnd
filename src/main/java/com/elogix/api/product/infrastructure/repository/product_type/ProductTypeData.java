package com.elogix.api.product.infrastructure.repository.product_type;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedData;
import com.elogix.api.product.config.ProductTypeUseCaseConfig;
import com.elogix.api.product.infrastructure.repository.product_category.ProductCategoryData;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "product_types")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE product_types SET is_deleted = true WHERE id=?")
@FilterDef(name = ProductTypeUseCaseConfig.DELETED_FILTER, parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = ProductTypeUseCaseConfig.DELETED_FILTER, condition = "is_deleted = :isDeleted")
public class ProductTypeData extends GenericNamedData {
    @Column(unique = true, length = 50, nullable = false)
    private String name;

    @Column(length = 50)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ToString.Exclude
    private ProductCategoryData category;

    @Builder.Default
    @JsonProperty("isMeasurable")
    @Column(name = "is_measurable", columnDefinition = "boolean DEFAULT false")
    private boolean isMeasurable = false;

    public ProductTypeData() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ProductTypeData that))
            return false;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
