package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedRepository;

@Repository
public interface CustomerDataJpaRepository
        extends GenericNamedRepository<CustomerData> {
    List<CustomerData> findAllByOrderByHitsDesc();

    Optional<CustomerData> findByEmail(String email);

    Optional<CustomerData> findByDocumentNumber(String documentNumber);

    void deleteByDocumentNumber(String documentNumber);

    @Transactional
    @Modifying
    @Query("UPDATE CustomerData c SET c.hits = :hits WHERE c.id = :id")
    int updateHits(Long id, Long hits);
}
