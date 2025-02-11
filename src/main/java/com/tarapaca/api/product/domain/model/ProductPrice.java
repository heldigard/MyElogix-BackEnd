package com.tarapaca.api.product.domain.model;

import java.math.BigDecimal;

import com.tarapaca.api.generics.domain.model.GenericEntity;
import com.tarapaca.api.product.infrastructure.repository.product_price.ProductPriceData;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link ProductPriceData}
 */

@Getter
@Setter
@SuperBuilder
public class ProductPrice extends GenericEntity {
    private Long priceListId;
    private String productRef;

    @Builder.Default
    private BigDecimal cmsPrice = new BigDecimal(0);

    @Builder.Default
    private BigDecimal unitPrice = new BigDecimal(0);

    @Builder.Default
    private Long waste = 0L;

    public ProductPrice() {
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
        ProductPrice that = (ProductPrice) obj;
        return java.util.Objects.equals(priceListId, that.priceListId) &&
                java.util.Objects.equals(productRef, that.productRef) &&
                java.util.Objects.equals(cmsPrice, that.cmsPrice) &&
                java.util.Objects.equals(unitPrice, that.unitPrice) &&
                java.util.Objects.equals(waste, that.waste);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), priceListId, productRef, cmsPrice, unitPrice, waste);
    }
}
