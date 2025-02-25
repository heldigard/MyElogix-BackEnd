package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city;

import org.springframework.stereotype.Service;

import com.elogix.api.customers.domain.model.City;
import com.elogix.api.customers.domain.model.gateways.CityGateway;
import com.elogix.api.customers.infrastructure.helpers.mappers.CityMapper;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;

/**
 * Implementation of the City Gateway interface that handles city-related data
 * operations.
 * Extends generic implementation for common CRUD operations.
 */
@Service
public class CityGatewayImpl
        extends GenericNamedGatewayImpl<City, CityData, CityDataJpaRepository, CityMapper>
        implements CityGateway {

    public CityGatewayImpl(GenericConfig<City, CityData, CityDataJpaRepository, CityMapper> config) {
        super(config);
    }
}
