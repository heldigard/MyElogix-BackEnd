package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood;

import java.util.Objects;

import com.tarapaca.api.customers.domain.model.CityBasic;
import com.tarapaca.api.customers.domain.model.DeliveryZoneBasic;
import com.tarapaca.api.customers.domain.model.Neighborhood;
import com.tarapaca.api.customers.domain.model.gateways.NeighborhoodGateway;
import com.tarapaca.api.customers.domain.usecase.CityBasicUseCase;
import com.tarapaca.api.customers.domain.usecase.DeliveryZoneBasicUseCase;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.NeighborhoodMapper;
import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

public class NeighborhoodGatewayImpl
        extends
        GenericNamedGatewayImpl<Neighborhood, NeighborhoodData, NeighborhoodDataJpaRepository, NeighborhoodMapper>
        implements NeighborhoodGateway {

    private final CityBasicUseCase cityUseCase;
    private final DeliveryZoneBasicUseCase zoneUseCase;

    public NeighborhoodGatewayImpl(
            NeighborhoodDataJpaRepository repository,
            NeighborhoodMapper mapper,
            CityBasicUseCase cityUseCase,
            DeliveryZoneBasicUseCase zoneUseCase,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
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
