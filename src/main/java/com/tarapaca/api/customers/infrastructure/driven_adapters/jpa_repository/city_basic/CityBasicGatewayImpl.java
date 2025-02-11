package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic;

import com.tarapaca.api.customers.domain.model.CityBasic;
import com.tarapaca.api.customers.domain.model.gateways.CityBasicGateway;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.CityBasicMapper;
import com.tarapaca.api.generics.infrastructure.repository.GenericNamedBasic.GenericNamedBasicGatewayImpl;

import jakarta.persistence.EntityManager;

public class CityBasicGatewayImpl
        extends GenericNamedBasicGatewayImpl<CityBasic, CityBasicData, CityBasicDataJpaRepository, CityBasicMapper>
        implements CityBasicGateway {

    public CityBasicGatewayImpl(
            CityBasicDataJpaRepository repository,
            CityBasicMapper mapper,
            EntityManager entityManager,
            String deletedFilter) {
        super(repository, mapper, entityManager, deletedFilter);
    }
}
