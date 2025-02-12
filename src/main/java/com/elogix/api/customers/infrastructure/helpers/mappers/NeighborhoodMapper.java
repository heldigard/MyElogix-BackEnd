package com.elogix.api.customers.infrastructure.helpers.mappers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.Neighborhood;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodData;
import com.elogix.api.generics.infrastructure.helpers.GenericNamedMapper;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class NeighborhoodMapper extends GenericNamedMapper<Neighborhood, NeighborhoodData> {
    private final CityBasicMapper cityMapper;
    private final DeliveryZoneBasicMapper deliveryZoneMapper;

    public NeighborhoodMapper(CityBasicMapper cityMapper,
            DeliveryZoneBasicMapper deliveryZoneMapper,
            UserBasicMapper userMapper) {
        super(Neighborhood.class, NeighborhoodData.class, userMapper);
        this.cityMapper = cityMapper;
        this.deliveryZoneMapper = deliveryZoneMapper;
    }

    @Override
    @Nullable
    public Neighborhood toDomain(@Nullable NeighborhoodData source, @NonNull Neighborhood target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getDeliveryZone())
                .ifPresent(deliveryZone -> target.setDeliveryZone(deliveryZoneMapper.toDomain(deliveryZone)));
        Optional.ofNullable(source.getCity())
                .ifPresent(city -> target.setCity(cityMapper.toDomain(city)));

        return target;
    }

    @Override
    @Nullable
    public NeighborhoodData toData(@Nullable Neighborhood source, @NonNull NeighborhoodData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getDeliveryZone())
                .ifPresent(deliveryZone -> target.setDeliveryZone(deliveryZoneMapper.toData(deliveryZone)));
        Optional.ofNullable(source.getCity())
                .ifPresent(city -> target.setCity(cityMapper.toData(city)));

        return target;
    }
}
