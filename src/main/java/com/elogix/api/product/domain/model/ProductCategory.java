package com.elogix.api.product.domain.model;

import com.elogix.api.generics.domain.model.GenericNamed;
import com.elogix.api.product.infrastructure.repository.product_category.ProductCategoryData;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link ProductCategoryData}
 */

@Getter
@Setter
@SuperBuilder
public class ProductCategory extends GenericNamed {
    private String description;

    public ProductCategory() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        if (!super.equals(obj))
            return false;
        ProductCategory that = (ProductCategory) obj;
        return java.util.Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), description);
    }
}
