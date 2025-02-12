package com.elogix.api.product.infrastructure.helper.mapper;

import com.elogix.api.delivery_orders.infrastructure.helpers.mappers.StatusMapper;
import com.elogix.api.product.domain.model.Product;
import com.elogix.api.product.infrastructure.repository.product.ProductData;
import com.elogix.api.generics.infrastructure.helpers.GenericEntityMapper;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import java.util.Optional;

@Component
public class ProductMapper extends GenericEntityMapper<Product, ProductData> {
    private final StatusMapper statusMapper;
    private final ProductTypeMapper productTypeMapper;

    public ProductMapper(UserBasicMapper userMapper,
                        StatusMapper statusMapper,
                        ProductTypeMapper productTypeMapper) {
        super(Product.class, ProductData.class, userMapper);
        this.statusMapper = statusMapper;
        this.productTypeMapper = productTypeMapper;
    }

    @Override
    @Nullable
    public Product toDomain(@Nullable ProductData source, @NonNull Product target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);
        Optional.ofNullable(source.getReference()).ifPresent(target::setReference);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        Optional.ofNullable(source.getType()).ifPresent(type -> target.setType(productTypeMapper.toDomain(type)));
        Optional.ofNullable(source.getStatus()).ifPresent(status -> target.setStatus(statusMapper.toDomain(status)));
        target.setHits(source.getHits() != null ? source.getHits() : 0);
        return target;
    }

    @Override
    @Nullable
    public ProductData toData(@Nullable Product source, @NonNull ProductData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);
        Optional.ofNullable(source.getReference()).ifPresent(target::setReference);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        Optional.ofNullable(source.getType()).ifPresent(type -> target.setType(productTypeMapper.toData(type)));
        Optional.ofNullable(source.getStatus()).ifPresent(status -> target.setStatus(statusMapper.toData(status)));
        target.setHits(source.getHits());
        return target;
    }
}
