package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic;

import org.springframework.stereotype.Repository;

import com.tarapaca.api.generics.infrastructure.repository.GenericNamedBasic.GenericNamedBasicRepository;

@Repository
public interface DeliveryZoneBasicDataJpaRepository
        extends GenericNamedBasicRepository<DeliveryZoneBasicData> {
}
