package com.elogix.api.product.infrastructure.repository.product_category;

import org.springframework.stereotype.Repository;

import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedRepository;

@Repository
public interface ProductCategoryDataJpaRepository
        extends GenericNamedRepository<ProductCategoryData> {

}
