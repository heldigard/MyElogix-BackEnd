package com.tarapaca.api.product.infrastructure.repository.product_type;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedRepository;
import com.tarapaca.api.product.infrastructure.repository.product_category.ProductCategoryData;

@Repository
public interface ProductTypeDataJpaRepository
        extends JpaRepository<ProductTypeData, Long>, GenericNamedRepository<ProductTypeData> {
    @Transactional
    @Modifying
    @Query("UPDATE ProductTypeData p SET p.category = :category WHERE p.id = :id")
    ProductTypeData updateCategory(ProductCategoryData category, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE ProductTypeData p SET p.isMeasurable = :isMeasurable WHERE p.id = :id")
    ProductTypeData updateIsMeasurable(Long id, boolean isMeasurable);

    @Query("SELECT p FROM ProductTypeData p WHERE p.category.name = :name")
    List<ProductTypeData> findByCategory(String name);
}
