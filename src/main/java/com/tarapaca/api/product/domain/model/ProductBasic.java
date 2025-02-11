package com.tarapaca.api.product.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tarapaca.api.delivery_orders.domain.model.Status;
import com.tarapaca.api.generics.domain.model.GenericEntity;
import com.tarapaca.api.product.infrastructure.repository.product_basic.ProductBasicData;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link ProductBasicData}
 */

@Getter
@Setter
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductBasic extends GenericEntity {
    private String reference;
    private String description;
    private ProductType type;
    private Status status;
    @Builder.Default
    private Long hits = 0L;
    @Builder.Default
    @JsonProperty("isActive")
    private boolean isActive = true;
    @Builder.Default
    @JsonProperty("isLowStock")
    private boolean isLowStock = false;

    public ProductBasic() {
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
        ProductBasic that = (ProductBasic) obj;
        return isActive == that.isActive &&
                isLowStock == that.isLowStock &&
                java.util.Objects.equals(reference, that.reference) &&
                java.util.Objects.equals(description, that.description) &&
                java.util.Objects.equals(type, that.type) &&
                java.util.Objects.equals(status, that.status) &&
                java.util.Objects.equals(hits, that.hits);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), reference, description, type, status, hits, isActive,
                isLowStock);
    }
}
