package com.elogix.api.product.infrastructure.helper.mapper;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.elogix.api.delivery_orders.infrastructure.helpers.mappers.StatusMapper;
import com.elogix.api.generics.infrastructure.helpers.GenericBasicMapper;
import com.elogix.api.product.domain.model.ProductBasic;
import com.elogix.api.product.infrastructure.repository.product_basic.ProductBasicData;

@Component
public class ProductBasicMapper extends GenericBasicMapper<ProductBasic, ProductBasicData> {
    private final StatusMapper statusMapper;
    private final ProductTypeMapper productTypeMapper;

    public ProductBasicMapper(StatusMapper statusMapper, ProductTypeMapper productTypeMapper) {
        super(ProductBasic.class, ProductBasicData.class);
        this.statusMapper = statusMapper;
        this.productTypeMapper = productTypeMapper;
    }

    @Override
    @Nullable
    public ProductBasic toDomain(@Nullable ProductBasicData source, @NonNull ProductBasic target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getReference()).ifPresent(target::setReference);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        Optional.ofNullable(source.getType()).ifPresent(type -> target.setType(productTypeMapper.toDomain(type)));
        Optional.ofNullable(source.getHits()).ifPresent(target::setHits);

        target.setActive(source.isActive());
        target.setLowStock(source.isLowStock());

        Optional.ofNullable(source.getStatus()).ifPresent(status -> target.setStatus(statusMapper.toDomain(status)));

        return target;
    }

    @Override
    @Nullable
    public ProductBasicData toData(@Nullable ProductBasic source, @NonNull ProductBasicData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getReference()).ifPresent(target::setReference);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        Optional.ofNullable(source.getHits()).ifPresent(target::setHits);

        target.setActive(source.isActive());
        target.setLowStock(source.isLowStock());

        Optional.ofNullable(source.getType())
                .ifPresent(productType -> target.setType(productTypeMapper.toData(productType)));

        Optional.ofNullable(source.getStatus()).ifPresent(status -> target.setStatus(statusMapper.toData(status)));

        return target;
    }
}
