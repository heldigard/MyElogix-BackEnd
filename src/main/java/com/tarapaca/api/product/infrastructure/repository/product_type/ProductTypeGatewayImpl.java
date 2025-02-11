package com.tarapaca.api.product.infrastructure.repository.product_type;

import java.util.List;

import org.hibernate.Session;

import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.tarapaca.api.product.domain.gateway.ProductTypeGateway;
import com.tarapaca.api.product.domain.model.ProductType;
import com.tarapaca.api.product.infrastructure.helper.mapper.ProductTypeMapper;
import com.tarapaca.api.product.infrastructure.repository.product_category.ProductCategoryData;
import com.tarapaca.api.product.infrastructure.repository.product_category.ProductCategoryDataJpaRepository;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

public class ProductTypeGatewayImpl
        extends GenericNamedGatewayImpl<ProductType, ProductTypeData, ProductTypeDataJpaRepository, ProductTypeMapper>
        implements ProductTypeGateway {
    private final ProductCategoryDataJpaRepository repoCategory;

    public ProductTypeGatewayImpl(
            ProductTypeDataJpaRepository repository,
            ProductTypeMapper mapper,
            ProductCategoryDataJpaRepository repoCategory,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
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
