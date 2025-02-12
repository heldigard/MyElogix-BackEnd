package com.elogix.api.product.infrastructure.helper.mapper;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.infrastructure.helpers.GenericNamedMapper;
import com.elogix.api.product.domain.model.ProductType;
import com.elogix.api.product.infrastructure.repository.product_type.ProductTypeData;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class ProductTypeMapper extends GenericNamedMapper<ProductType, ProductTypeData> {
    private final ProductCategoryMapper categoryMapper;

    public ProductTypeMapper(UserBasicMapper userMapper, ProductCategoryMapper categoryMapper) {
        super(ProductType.class, ProductTypeData.class, userMapper);
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Nullable
    public ProductType toDomain(@Nullable ProductTypeData source, @NonNull ProductType target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        Optional.ofNullable(source.getCategory()).ifPresent(
                category -> target.setCategory(categoryMapper.toDomain(category))
        );
        target.setMeasurable(source.isMeasurable());
        return target;
    }

    @Override
    @Nullable
    public ProductTypeData toData(@Nullable ProductType source, @NonNull ProductTypeData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        Optional.ofNullable(source.getCategory()).ifPresent(
                category -> target.setCategory(categoryMapper.toData(category))
        );
        target.setMeasurable(source.isMeasurable());
        return target;
    }
}
