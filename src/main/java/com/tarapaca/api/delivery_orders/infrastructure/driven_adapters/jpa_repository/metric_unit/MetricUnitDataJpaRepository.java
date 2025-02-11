package com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.metric_unit;

import org.springframework.stereotype.Repository;

import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedRepository;

@Repository
public interface MetricUnitDataJpaRepository
        extends GenericNamedRepository<MetricUnitData> {
}
