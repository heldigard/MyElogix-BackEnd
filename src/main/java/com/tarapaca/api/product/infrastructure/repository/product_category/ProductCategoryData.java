package com.tarapaca.api.product.infrastructure.repository.product_category;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "product_categories")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE product_categories SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedProductCategoryFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedProductCategoryFilter", condition = "is_deleted = :isDeleted")
public class ProductCategoryData extends GenericNamedData {
    @Column(unique = true, length = 50, nullable = false)
    private String name;

    @Column(length = 50)
    private String description;

    public ProductCategoryData() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ProductCategoryData that))
            return false;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
