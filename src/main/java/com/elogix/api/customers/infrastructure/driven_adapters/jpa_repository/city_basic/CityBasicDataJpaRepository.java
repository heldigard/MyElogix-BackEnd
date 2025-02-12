package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic;

import org.springframework.stereotype.Repository;

import com.elogix.api.generics.infrastructure.repository.GenericNamedBasic.GenericNamedBasicRepository;

@Repository
public interface CityBasicDataJpaRepository
        extends GenericNamedBasicRepository<CityBasicData> {
}
