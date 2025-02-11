package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city;

import org.springframework.stereotype.Service;

import com.tarapaca.api.customers.domain.model.City;
import com.tarapaca.api.customers.domain.model.gateways.CityGateway;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.CityMapper;
import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

/**
 * Implementation of the City Gateway interface that handles city-related data
 * operations.
 * Extends generic implementation for common CRUD operations.
 */
@Service
public class CityGatewayImpl
        extends GenericNamedGatewayImpl<City, CityData, CityDataJpaRepository, CityMapper>
        implements CityGateway {

    public CityGatewayImpl(
            CityDataJpaRepository repository,
            CityMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
    }
}
