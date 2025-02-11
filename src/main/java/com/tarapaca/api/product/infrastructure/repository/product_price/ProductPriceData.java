package com.tarapaca.api.product.infrastructure.repository.product_price;

import java.math.BigDecimal;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.price_list.PriceListData;
import com.tarapaca.api.generics.infrastructure.repository.GenericEntity.GenericEntityData;

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
@Table(name = "product_prices")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE product_prices SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedProductPriceFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedProductPriceFilter", condition = "is_deleted = :isDeleted")
public class ProductPriceData extends GenericEntityData {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pricelist_id", referencedColumnName = "id")
    @ToString.Exclude
    private PriceListData priceList;

    @NotBlank
    @Column(name = "productRef", length = 50, unique = true, nullable = false)
    private String productRef;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal cmsPrice = new BigDecimal(0);

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal unitPrice = new BigDecimal(0);

    @Builder.Default
    @Column(columnDefinition = "bigint default 0")
    private Long waste = 0L;

    public ProductPriceData() {
        super();
    }
}
