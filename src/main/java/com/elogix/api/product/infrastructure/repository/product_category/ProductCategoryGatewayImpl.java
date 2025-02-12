package com.elogix.api.product.infrastructure.repository.product_category;

import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.elogix.api.product.domain.gateway.ProductCategoryGateway;
import com.elogix.api.product.domain.model.ProductCategory;
import com.elogix.api.product.infrastructure.helper.mapper.ProductCategoryMapper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

public class ProductCategoryGatewayImpl
        extends
        GenericNamedGatewayImpl<ProductCategory, ProductCategoryData, ProductCategoryDataJpaRepository, ProductCategoryMapper>
        implements ProductCategoryGateway {

    public ProductCategoryGatewayImpl(
            ProductCategoryDataJpaRepository repository,
            ProductCategoryMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
    }
}
