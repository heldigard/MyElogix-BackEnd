package com.tarapaca.api.delivery_order.infrastructure.helper.mapper;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.tarapaca.api.customers.infrastructure.helpers.mappers.BranchOfficeMapper;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.CustomerMapper;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.DeliveryZoneBasicMapper;
import com.tarapaca.api.delivery_order.domain.model.DeliveryOrder;
import com.tarapaca.api.delivery_order.infrastructure.repository.delivery_order.DeliveryOrderData;
import com.tarapaca.api.delivery_orders.infrastructure.helpers.mappers.StatusMapper;
import com.tarapaca.api.generics.infrastructure.helpers.GenericProductionMapper;
import com.tarapaca.api.product_order.infrastructure.helper.ProductOrderMapper;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class DeliveryOrderMapper extends GenericProductionMapper<DeliveryOrder, DeliveryOrderData> {
        private final CustomerMapper customerMapper;
        private final DeliveryZoneBasicMapper deliveryZoneBasicMapper;
        private final BranchOfficeMapper branchOfficeMapper;
        private final ProductOrderMapper productOrderMapper;
        private final UserBasicMapper userMapper;

        public DeliveryOrderMapper(CustomerMapper customerMapper,
                        DeliveryZoneBasicMapper deliveryZoneBasicMapper,
                        BranchOfficeMapper branchOfficeMapper,
                        ProductOrderMapper productOrderMapper,
                        UserBasicMapper userMapper,
                        StatusMapper statusMapper) {
                super(DeliveryOrder.class, DeliveryOrderData.class, userMapper, statusMapper);
                this.customerMapper = customerMapper;
                this.deliveryZoneBasicMapper = deliveryZoneBasicMapper;
                this.branchOfficeMapper = branchOfficeMapper;
                this.productOrderMapper = productOrderMapper;
                this.userMapper = userMapper;
        }

        @Override
        @Nullable
        public DeliveryOrder toDomain(@Nullable DeliveryOrderData source, @NonNull DeliveryOrder target) {
                if (source == null) {
                        return null;
                }
                super.toDomain(source, target);

                Optional.ofNullable(source.getBilledAt()).ifPresent(target::setBilledAt);
                Optional.ofNullable(source.getBilledBy())
                                .ifPresent(user -> target.setBilledBy(userMapper.toDomain(user)));
                Optional.ofNullable(source.getCustomer())
                                .ifPresent(customer -> target.setCustomer(customerMapper.toDomain(customer)));
                Optional.ofNullable(source.getDeliveryZone())
                                .ifPresent(zone -> target.setDeliveryZone(deliveryZoneBasicMapper.toDomain(zone)));
                Optional.ofNullable(source.getBranchOffice())
                                .ifPresent(office -> target.setBranchOffice(branchOfficeMapper.toDomain(office)));
                Optional.ofNullable(source.getProductOrders())
                                .ifPresent(products -> target
                                                .setProductOrders(productOrderMapper.toDomain(products).stream()
                                                                .toList()));
                Optional.ofNullable(source.getGeneralObservations())
                                .ifPresent(target::setGeneralObservations);
                Optional.ofNullable(source.getTotalPrice())
                                .ifPresent(target::setTotalPrice);

                target.setBilled(source.isBilled());

                return target;
        }

        @Override
        @Nullable
        public DeliveryOrderData toData(@Nullable DeliveryOrder source, @NonNull DeliveryOrderData target) {
                if (source == null) {
                        return null;
                }
                super.toData(source, target);

                Optional.ofNullable(source.getBilledAt())
                                .ifPresent(target::setBilledAt);
                Optional.ofNullable(source.getBilledBy())
                                .ifPresent(user -> target.setBilledBy(userMapper.toData(user)));
                Optional.ofNullable(source.getCustomer())
                                .ifPresent(customer -> target.setCustomer(customerMapper.toData(customer)));
                Optional.ofNullable(source.getDeliveryZone())
                                .ifPresent(zone -> target.setDeliveryZone(deliveryZoneBasicMapper.toData(zone)));
                Optional.ofNullable(source.getBranchOffice())
                                .ifPresent(office -> target.setBranchOffice(branchOfficeMapper.toData(office)));
                Optional.ofNullable(source.getProductOrders())
                                .ifPresent(products -> target.setProductOrders(
                                                productOrderMapper.toData(products).stream().toList()));
                Optional.ofNullable(source.getGeneralObservations())
                                .ifPresent(target::setGeneralObservations);
                Optional.ofNullable(source.getTotalPrice())
                                .ifPresent(target::setTotalPrice);

                target.setBilled(source.isBilled());

                return target;
        }
}
