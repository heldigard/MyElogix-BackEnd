package com.elogix.api.product.infrastructure.repository.product_type;

import java.util.List;

import org.hibernate.Session;

import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.elogix.api.product.domain.gateway.ProductTypeGateway;
import com.elogix.api.product.domain.model.ProductType;
import com.elogix.api.product.infrastructure.helper.mapper.ProductTypeMapper;
import com.elogix.api.product.infrastructure.repository.product_category.ProductCategoryData;
import com.elogix.api.product.infrastructure.repository.product_category.ProductCategoryDataJpaRepository;

public class ProductTypeGatewayImpl
        extends GenericNamedGatewayImpl<ProductType, ProductTypeData, ProductTypeDataJpaRepository, ProductTypeMapper>
        implements ProductTypeGateway {
    private final ProductCategoryDataJpaRepository repoCategory;

    public ProductTypeGatewayImpl(
            GenericConfig<ProductType, ProductTypeData, ProductTypeDataJpaRepository, ProductTypeMapper> config,
            ProductCategoryDataJpaRepository repoCategory) {
        super(config);
        this.repoCategory = repoCategory;
    }

    @Override
    public ProductType updateCategory(String name, Long id) {
        ProductCategoryData category = repoCategory.findByName(name)
                .orElseGet(() -> ProductCategoryData.builder().name(name).build());

        return mapper.toDomain(repository.updateCategory(category, id));
    }

    @Override
    public ProductType updateIsMeasurable(Long id, boolean isMeasurable) {
        return mapper.toDomain(repository.updateIsMeasurable(id, isMeasurable));
    }

    @Override
    public List<ProductType> findByCategory(String name, boolean isDeleted) {
        List<ProductTypeData> productTypeDataList;
        try (Session session = setDeleteFilter(isDeleted)) {
            productTypeDataList = repository.findByCategory(name);
            session.disableFilter(this.deletedFilter);
        }

        return productTypeDataList.stream()
                .map(mapper::toDomain)
                .toList();
    }
}
