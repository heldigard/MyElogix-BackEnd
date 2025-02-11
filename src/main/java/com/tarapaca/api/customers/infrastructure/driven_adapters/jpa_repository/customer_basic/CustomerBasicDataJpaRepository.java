package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.customer_basic;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tarapaca.api.generics.infrastructure.repository.GenericNamedBasic.GenericNamedBasicRepository;

@Repository
public interface CustomerBasicDataJpaRepository
        extends GenericNamedBasicRepository<CustomerBasicData> {
    Optional<CustomerBasicData> findByEmail(String email);

    Optional<CustomerBasicData> findByDocumentNumber(String documentNumber);
}
