package com.elogix.api.delivery_orders.domain.model;

import java.util.HashSet;
import java.util.Set;

import com.elogix.api.generics.domain.model.GenericNamed;
import com.elogix.api.product.domain.model.ProductPrice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.price_list.PriceListData}
 */

@Getter
@Setter
@SuperBuilder
public class PriceList extends GenericNamed {
    private String description;
    private Integer year;
    private Integer month;
    @Builder.Default
    private Set<ProductPrice> productPriceList = new HashSet<>();
    @Builder.Default
    private boolean isActive = true;

    public PriceList() {
        super();
    }
}
