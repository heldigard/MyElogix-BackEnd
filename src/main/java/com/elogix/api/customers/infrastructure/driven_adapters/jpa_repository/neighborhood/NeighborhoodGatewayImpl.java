package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood;

import java.util.Objects;

import com.elogix.api.customers.domain.model.CityBasic;
import com.elogix.api.customers.domain.model.DeliveryZoneBasic;
import com.elogix.api.customers.domain.model.Neighborhood;
import com.elogix.api.customers.domain.model.gateways.NeighborhoodGateway;
import com.elogix.api.customers.domain.usecase.CityBasicUseCase;
import com.elogix.api.customers.domain.usecase.DeliveryZoneBasicUseCase;
import com.elogix.api.customers.infrastructure.helpers.mappers.NeighborhoodMapper;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;

public class NeighborhoodGatewayImpl
        extends
        GenericNamedGatewayImpl<Neighborhood, NeighborhoodData, NeighborhoodDataJpaRepository, NeighborhoodMapper>
        implements NeighborhoodGateway {

    private final CityBasicUseCase cityUseCase;
    private final DeliveryZoneBasicUseCase zoneUseCase;

    public NeighborhoodGatewayImpl(
            GenericConfig<Neighborhood, NeighborhoodData, NeighborhoodDataJpaRepository, NeighborhoodMapper> config,
            CityBasicUseCase cityUseCase,
            DeliveryZoneBasicUseCase zoneUseCase) {
        super(config);
        this.cityUseCase = cityUseCase;
        this.zoneUseCase = zoneUseCase;
    }

    @Override
    public Neighborhood update(Neighborhood neighborhood) {
        Neighborhood existing = findById(neighborhood.getId(), false);

        if (neighborhood != null && existing.getName() != null
                && !(existing.getName().equals(neighborhood.getName()))) {
            existing.setName(neighborhood.getName());
        }
        if (neighborhood.getCity() != null
                && (!Objects.equals(existing.getCity().getId(), neighborhood.getCity().getId()))) {
            CityBasic city = cityUseCase.findById(neighborhood.getCity().getId(), false);
            existing.setCity(city);
        }
        if (neighborhood.getDeliveryZone() != null
                && !Objects.equals(existing.getDeliveryZone().getId(), neighborhood.getDeliveryZone().getId())) {
            DeliveryZoneBasic deliveryZone = zoneUseCase.findById(neighborhood.getDeliveryZone().getId(), false);
            existing.setDeliveryZone(deliveryZone);
        }

        return save(existing);
    }
}
