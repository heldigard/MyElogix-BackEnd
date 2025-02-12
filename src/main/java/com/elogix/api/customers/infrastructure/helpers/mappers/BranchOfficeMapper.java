package com.elogix.api.customers.infrastructure.helpers.mappers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.BranchOffice;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeData;
import com.elogix.api.generics.infrastructure.helpers.GenericEntityMapper;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;

import lombok.Getter;

@Component
@Getter
public class BranchOfficeMapper extends GenericEntityMapper<BranchOffice, BranchOfficeData> {
    private final ContactPersonBasicMapper contactPersonMapper;
    private final NeighborhoodMapper neighborhoodMapper;
    private final CityBasicMapper cityMapper;
    private final DeliveryZoneBasicMapper deliveryZoneMapper;

    public BranchOfficeMapper(UserBasicMapper userMapper,
            ContactPersonBasicMapper contactPersonMapper,
            NeighborhoodMapper neighborhoodMapper,
            CityBasicMapper cityMapper,
            DeliveryZoneBasicMapper deliveryZoneMapper) {
        super(BranchOffice.class, BranchOfficeData.class, userMapper);
        this.contactPersonMapper = contactPersonMapper;
        this.neighborhoodMapper = neighborhoodMapper;
        this.cityMapper = cityMapper;
        this.deliveryZoneMapper = deliveryZoneMapper;
    }

    @Override
    @Nullable
    public BranchOffice toDomain(@Nullable BranchOfficeData source, @NonNull BranchOffice target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getCustomerId()).ifPresent(target::setCustomerId);
        Optional.ofNullable(source.getAddress()).ifPresent(target::setAddress);
        Optional.ofNullable(source.getContactPerson())
                        .ifPresent(contactPerson -> target
                                        .setContactPerson(contactPersonMapper.toDomain(contactPerson)));
        Optional.ofNullable(source.getCity()).ifPresent(city -> target.setCity(cityMapper.toDomain(city)));
        Optional.ofNullable(source.getNeighborhood())
                        .ifPresent(neighborhood -> target.setNeighborhood(neighborhoodMapper.toDomain(neighborhood)));
        Optional.ofNullable(source.getDeliveryZone())
                        .ifPresent(deliveryZone -> target.setDeliveryZone(deliveryZoneMapper.toDomain(deliveryZone)));

            return target;
    }

    @Override
    @Nullable
    public BranchOfficeData toData(@Nullable BranchOffice source, @NonNull BranchOfficeData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getCustomerId()).ifPresent(target::setCustomerId);
        Optional.ofNullable(source.getAddress()).ifPresent(target::setAddress);
        Optional.ofNullable(source.getContactPerson())
                .ifPresent(contactPerson -> target.setContactPerson(contactPersonMapper.toData(contactPerson)));
        Optional.ofNullable(source.getCity()).ifPresent(city -> target.setCity(cityMapper.toData(city)));
        Optional.ofNullable(source.getNeighborhood())
                .ifPresent(neighborhood -> target.setNeighborhood(neighborhoodMapper.toData(neighborhood)));
        Optional.ofNullable(source.getDeliveryZone())
                .ifPresent(deliveryZone -> target.setDeliveryZone(deliveryZoneMapper.toData(deliveryZone)));

            return target;
    }
}
