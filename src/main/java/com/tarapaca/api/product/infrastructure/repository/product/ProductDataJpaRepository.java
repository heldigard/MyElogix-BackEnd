package com.tarapaca.api.product.infrastructure.repository.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tarapaca.api.generics.infrastructure.repository.GenericStatus.GenericStatusRepository;

@Repository
public interface ProductDataJpaRepository
        extends GenericStatusRepository<ProductData> {

    Optional<ProductData> findByReference(String reference);

    void deleteByReference(String reference);

    @Transactional
    @Modifying
    @Query("UPDATE ProductData p SET p.hits = :hits where p.id = :id")
    int updateHits(Long id, Long hits);
}
