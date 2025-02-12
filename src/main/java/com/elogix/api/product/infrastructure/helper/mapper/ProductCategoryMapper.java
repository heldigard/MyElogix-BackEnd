package com.elogix.api.product.infrastructure.helper.mapper;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.infrastructure.helpers.GenericNamedMapper;
import com.elogix.api.product.domain.model.ProductCategory;
import com.elogix.api.product.infrastructure.repository.product_category.ProductCategoryData;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class ProductCategoryMapper extends GenericNamedMapper<ProductCategory, ProductCategoryData> {

    public ProductCategoryMapper(UserBasicMapper userMapper) {
        super(ProductCategory.class, ProductCategoryData.class, userMapper);
    }

    @Override
    @Nullable
    public ProductCategory toDomain(@Nullable ProductCategoryData source, @NonNull ProductCategory target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        return target;
    }

    @Override
    @Nullable
    public ProductCategoryData toData(@Nullable ProductCategory source, @NonNull ProductCategoryData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        return target;
    }
}
