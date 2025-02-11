package com.tarapaca.api.delivery_order.infrastructure.helper.mapper;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.tarapaca.api.customers.infrastructure.helpers.mappers.CustomerBasicMapper;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.DeliveryZoneBasicMapper;
import com.tarapaca.api.delivery_order.domain.model.DeliveryOrderBasic;
import com.tarapaca.api.delivery_order.infrastructure.repository.delivery_order_basic.DeliveryOrderBasicData;
import com.tarapaca.api.delivery_orders.infrastructure.helpers.mappers.StatusMapper;
import com.tarapaca.api.generics.infrastructure.helpers.GenericProductionMapper;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class DeliveryOrderBasicMapper extends GenericProductionMapper<DeliveryOrderBasic, DeliveryOrderBasicData> {
    private final CustomerBasicMapper customerMapper;
    private final DeliveryZoneBasicMapper deliveryZoneBasicMapper;
    private final UserBasicMapper userMapper;
    private final StatusMapper statusMapper;

    public DeliveryOrderBasicMapper(CustomerBasicMapper customerMapper,
            DeliveryZoneBasicMapper deliveryZoneBasicMapper,
            UserBasicMapper userMapper,
            StatusMapper statusMapper) {
        super(DeliveryOrderBasic.class, DeliveryOrderBasicData.class, userMapper, statusMapper);
        this.customerMapper = customerMapper;
        this.deliveryZoneBasicMapper = deliveryZoneBasicMapper;
        this.userMapper = userMapper;
        this.statusMapper = statusMapper;
    }

    @Override
    @Nullable
    public DeliveryOrderBasic toDomain(@Nullable DeliveryOrderBasicData source, @NonNull DeliveryOrderBasic target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getBilledAt()).ifPresent(target::setBilledAt);
        Optional.ofNullable(source.getBilledBy()).ifPresent(user -> target.setBilledBy(userMapper.toDomain(user)));
        Optional.ofNullable(source.getCustomer())
                .ifPresent(customer -> target.setCustomer(customerMapper.toDomain(customer)));
        Optional.ofNullable(source.getDeliveryZone())
                .ifPresent(zone -> target.setDeliveryZone(deliveryZoneBasicMapper.toDomain(zone)));
        Optional.ofNullable(source.getStatus()).ifPresent(status -> target.setStatus(statusMapper.toDomain(status)));

        target.setBilled(source.isBilled());

        return target;
    }

    @Override
    @Nullable
    public DeliveryOrderBasicData toData(@Nullable DeliveryOrderBasic source, @NonNull DeliveryOrderBasicData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getBilledAt()).ifPresent(target::setBilledAt);
        Optional.ofNullable(source.getBilledBy()).ifPresent(user -> target.setBilledBy(userMapper.toData(user)));
        Optional.ofNullable(source.getCustomer())
                .ifPresent(customer -> target.setCustomer(customerMapper.toData(customer)));
        Optional.ofNullable(source.getDeliveryZone())
                .ifPresent(zone -> target.setDeliveryZone(deliveryZoneBasicMapper.toData(zone)));
        Optional.ofNullable(source.getStatus()).ifPresent(status -> target.setStatus(statusMapper.toData(status)));

        target.setBilled(source.isBilled());

        return target;
    }
}
