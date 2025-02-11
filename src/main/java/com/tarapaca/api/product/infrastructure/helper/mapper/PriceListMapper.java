package com.tarapaca.api.product.infrastructure.helper.mapper;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.tarapaca.api.delivery_orders.domain.model.PriceList;
import com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.price_list.PriceListData;
import com.tarapaca.api.generics.infrastructure.helpers.GenericNamedMapper;
import com.tarapaca.api.product.domain.model.ProductPrice;
import com.tarapaca.api.product.infrastructure.repository.product_price.ProductPriceData;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class PriceListMapper extends GenericNamedMapper<PriceList, PriceListData> {
    private final ProductPriceMapper productPriceMapper;

    public PriceListMapper(UserBasicMapper userMapper, ProductPriceMapper productPriceMapper) {
        super(PriceList.class, PriceListData.class, userMapper);
        this.productPriceMapper = productPriceMapper;
    }

    @Override
    @Nullable
    public PriceList toDomain(@Nullable PriceListData source, @NonNull PriceList target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);
        Optional.ofNullable(source.getYear()).ifPresent(target::setYear);
        Optional.ofNullable(source.getMonth()).ifPresent(target::setMonth);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        target.setActive(source.isActive());

        if (source.getProductPriceList() != null) {
            Set<ProductPrice> productPrices = source.getProductPriceList().stream()
                    .map(productPriceMapper::toDomain)
                    .collect(Collectors.toSet());
            target.setProductPriceList(productPrices);
        }
        return target;
    }

    @Override
    @Nullable
    public PriceListData toData(@Nullable PriceList source, @NonNull PriceListData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);
        Optional.ofNullable(source.getYear()).ifPresent(target::setYear);
        Optional.ofNullable(source.getMonth()).ifPresent(target::setMonth);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        target.setActive(source.isActive());

        if (source.getProductPriceList() != null) {
            Set<ProductPriceData> productPrices = source.getProductPriceList().stream()
                    .map(productPriceMapper::toData)
                    .collect(Collectors.toSet());
            target.setProductPriceList(productPrices);
        }
        return target;
    }
}
