package com.tarapaca.api.customers.infrastructure.helpers.mappers;

import com.tarapaca.api.customers.domain.model.City;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityData;
import com.tarapaca.api.generics.infrastructure.helpers.GenericNamedMapper;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserBasicMapper;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Getter
public class CityMapper extends GenericNamedMapper<City, CityData> {
    private final NeighborhoodMapper neighborhoodMapper;

    public CityMapper(UserBasicMapper userMapper, NeighborhoodMapper neighborhoodMapper) {
        super(City.class, CityData.class, userMapper);
        this.neighborhoodMapper = neighborhoodMapper;
    }

    @Override
    @Nullable
    public City toDomain(@Nullable CityData source, @NonNull City target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        target.setNeighborhoodList(
            Optional.ofNullable(source.getNeighborhoodList())
                .orElse(Collections.emptySet())
                .stream()
                        .map(neighborhoodMapper::toDomain)
                .collect(Collectors.toSet())
        );

        return target;
    }

    @Override
    @Nullable
    public CityData toData(@Nullable City source, @NonNull CityData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        target.setNeighborhoodList(
            Optional.ofNullable(source.getNeighborhoodList())
                .orElse(Collections.emptySet())
                .stream()
                .map(neighborhoodMapper::toData)
                .collect(Collectors.toSet())
        );

        return target;
    }
}
