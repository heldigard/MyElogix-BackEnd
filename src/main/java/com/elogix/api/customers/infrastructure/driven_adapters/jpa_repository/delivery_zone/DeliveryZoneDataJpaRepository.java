package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone;

import org.springframework.stereotype.Repository;

import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedRepository;

@Repository
public interface DeliveryZoneDataJpaRepository
        extends GenericNamedRepository<DeliveryZoneData> {
}
