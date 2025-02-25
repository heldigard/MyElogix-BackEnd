package com.elogix.api.product.infrastructure.repository.product_category;

import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.elogix.api.product.domain.gateway.ProductCategoryGateway;
import com.elogix.api.product.domain.model.ProductCategory;
import com.elogix.api.product.infrastructure.helper.mapper.ProductCategoryMapper;

public class ProductCategoryGatewayImpl
        extends
        GenericNamedGatewayImpl<ProductCategory, ProductCategoryData, ProductCategoryDataJpaRepository, ProductCategoryMapper>
        implements ProductCategoryGateway {

    public ProductCategoryGatewayImpl(
            GenericConfig<ProductCategory, ProductCategoryData, ProductCategoryDataJpaRepository, ProductCategoryMapper> config) {
        super(config);
    }
}
