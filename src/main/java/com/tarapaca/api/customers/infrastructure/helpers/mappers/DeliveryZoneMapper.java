package com.tarapaca.api.customers.infrastructure.helpers.mappers;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.tarapaca.api.customers.domain.model.DeliveryZone;
import com.tarapaca.api.customers.domain.model.Neighborhood;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone.DeliveryZoneData;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodData;
import com.tarapaca.api.generics.infrastructure.helpers.GenericNamedMapper;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class DeliveryZoneMapper extends GenericNamedMapper<DeliveryZone, DeliveryZoneData> {
    private final NeighborhoodMapper neighborhoodMapper;

    public DeliveryZoneMapper(NeighborhoodMapper neighborhoodMapper, UserBasicMapper userMapper) {
        super(DeliveryZone.class, DeliveryZoneData.class, userMapper);
        this.neighborhoodMapper = neighborhoodMapper;
    }

    @Override
    @Nullable
    public DeliveryZone toDomain(@Nullable DeliveryZoneData source, @NonNull DeliveryZone target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Set<Neighborhood> neighborhoods = Optional.ofNullable(source.getNeighborhoodList())
                .orElse(Collections.emptySet())
                .stream()
                .map(neighborhoodMapper::toDomain)
                .collect(Collectors.toSet());
        target.setNeighborhoodList(neighborhoods);

        return target;
    }

    @Override
    @Nullable
    public DeliveryZoneData toData(@Nullable DeliveryZone source, @NonNull DeliveryZoneData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Set<NeighborhoodData> neighborhoods = Optional.ofNullable(source.getNeighborhoodList())
                .orElse(Collections.emptySet())
                .stream()
                .map(neighborhoodMapper::toData)
                .collect(Collectors.toSet());
        target.setNeighborhoodList(neighborhoods);

        return target;
    }
}
