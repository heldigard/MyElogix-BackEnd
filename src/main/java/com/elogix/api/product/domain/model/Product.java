package com.elogix.api.product.domain.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.elogix.api.generics.domain.model.GenericStatus;
import com.elogix.api.product.infrastructure.repository.product.ProductData;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link ProductData}
 */
@Getter
@Setter
@SuperBuilder
public class Product extends GenericStatus {
    private String reference;
    private String description;
    private ProductType type;
    private ProductPrice price;

    @Builder.Default
    private Long hits = 0L;

    @Builder.Default
    @JsonProperty("isActive")
    private boolean isActive = true;

    @Builder.Default
    @JsonProperty("isLowStock")
    private boolean isLowStock = false;

    public Product() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Product product = (Product) o;
        return isActive == product.isActive &&
                isLowStock == product.isLowStock &&
                Objects.equals(reference, product.reference) &&
                Objects.equals(description, product.description) &&
                Objects.equals(type, product.type) &&
                Objects.equals(price, product.price) &&
                Objects.equals(hits, product.hits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), reference, description, type, price, hits, isActive, isLowStock);
    }
}
