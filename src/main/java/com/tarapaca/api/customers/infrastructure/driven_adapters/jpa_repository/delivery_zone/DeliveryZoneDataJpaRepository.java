package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone;

import org.springframework.stereotype.Repository;

import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedRepository;

@Repository
public interface DeliveryZoneDataJpaRepository
        extends GenericNamedRepository<DeliveryZoneData> {
}
