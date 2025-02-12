package com.elogix.api.product.infrastructure.repository.product_basic;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.elogix.api.generics.infrastructure.repository.GenericBasic.GenericBasicRepository;

@Repository
public interface ProductBasicDataJpaRepository
        extends GenericBasicRepository<ProductBasicData> {
    Optional<ProductBasicData> findByReference(String reference);
}
