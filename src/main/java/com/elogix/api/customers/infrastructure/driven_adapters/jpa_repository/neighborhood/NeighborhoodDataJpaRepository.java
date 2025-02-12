package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood;

import org.springframework.stereotype.Repository;

import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedRepository;

@Repository
public interface NeighborhoodDataJpaRepository
        extends GenericNamedRepository<NeighborhoodData> {
}
