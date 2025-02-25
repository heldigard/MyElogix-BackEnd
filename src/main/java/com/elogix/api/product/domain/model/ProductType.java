package com.elogix.api.product.domain.model;

import java.util.Objects;

import com.elogix.api.generics.domain.model.GenericNamed;
import com.elogix.api.product.infrastructure.repository.product_type.ProductTypeData;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link ProductTypeData}
 */

@Getter
@Setter
@SuperBuilder
public class ProductType extends GenericNamed {
    private String description;
    private ProductCategory category;

    @Builder.Default
    @JsonProperty("isMeasurable")
    private boolean isMeasurable = false;

    public ProductType() {
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
        ProductType that = (ProductType) obj;
        return isMeasurable == that.isMeasurable &&
                Objects.equals(description, that.description) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, category, isMeasurable);
    }

    @Override
    public String toString() {
        return "ProductType{" +
                super.toString() +
                ", isMeasurable=" + isMeasurable +
                '}';
    }
}
