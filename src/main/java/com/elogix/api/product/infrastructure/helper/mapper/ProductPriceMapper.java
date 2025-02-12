package com.elogix.api.product.infrastructure.helper.mapper;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.price_list.PriceListData;
import com.elogix.api.generics.infrastructure.helpers.GenericEntityMapper;
import com.elogix.api.product.domain.model.ProductPrice;
import com.elogix.api.product.infrastructure.repository.product_price.ProductPriceData;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class ProductPriceMapper extends GenericEntityMapper<ProductPrice, ProductPriceData> {

    public ProductPriceMapper(UserBasicMapper userMapper) {
        super(ProductPrice.class, ProductPriceData.class, userMapper);
    }

    @Override
    @Nullable
    public ProductPrice toDomain(@Nullable ProductPriceData source, @NonNull ProductPrice target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);
        Optional.ofNullable(source.getPriceList().getId()).ifPresent(target::setPriceListId);
        Optional.ofNullable(source.getProductRef()).ifPresent(target::setProductRef);
        Optional.ofNullable(source.getWaste()).ifPresent(target::setWaste);
        Optional.ofNullable(source.getCmsPrice()).ifPresent(target::setCmsPrice);
        Optional.ofNullable(source.getUnitPrice()).ifPresent(target::setUnitPrice);
        return target;
    }

    @Override
    @Nullable
    public ProductPriceData toData(@Nullable ProductPrice source, @NonNull ProductPriceData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);
        Optional.ofNullable(source.getPriceListId()).ifPresent(priceListId -> {
            if (target.getPriceList() == null) {
                target.setPriceList(new PriceListData());
            }
            target.getPriceList().setId(priceListId);
        });
        Optional.ofNullable(source.getProductRef()).ifPresent(target::setProductRef);
        Optional.ofNullable(source.getWaste()).ifPresent(target::setWaste);
        Optional.ofNullable(source.getCmsPrice()).ifPresent(target::setCmsPrice);
        Optional.ofNullable(source.getUnitPrice()).ifPresent(target::setUnitPrice);
        return target;
    }
}
