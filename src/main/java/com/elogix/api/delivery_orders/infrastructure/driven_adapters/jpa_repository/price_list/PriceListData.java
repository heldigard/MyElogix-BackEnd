package com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.price_list;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.elogix.api.delivery_orders.application.config.PriceListUseCaseConfig;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedData;
import com.elogix.api.product.infrastructure.repository.product_price.ProductPriceData;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
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
@Table(name = "price_lists")
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE price_lists SET is_deleted = true WHERE id=?")
@FilterDef(name = PriceListUseCaseConfig.DELETED_FILTER, parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = PriceListUseCaseConfig.DELETED_FILTER, condition = "is_deleted = :isDeleted")
public class PriceListData extends GenericNamedData {
    @NotBlank
    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;

    @Column(length = 150)
    private String description;

    private Integer year;
    private Integer month;

    @OneToMany(mappedBy = "priceList", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<ProductPriceData> productPriceList = new HashSet<>();

    @Builder.Default
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive = true;

    public PriceListData() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PriceListData that))
            return false;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
