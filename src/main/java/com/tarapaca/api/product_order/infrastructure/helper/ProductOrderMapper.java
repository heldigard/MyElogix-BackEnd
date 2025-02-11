package com.tarapaca.api.product_order.infrastructure.helper;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.tarapaca.api.delivery_orders.infrastructure.helpers.mappers.MeasureDetailMapper;
import com.tarapaca.api.delivery_orders.infrastructure.helpers.mappers.MetricUnitMapper;
import com.tarapaca.api.delivery_orders.infrastructure.helpers.mappers.StatusMapper;
import com.tarapaca.api.generics.infrastructure.helpers.GenericProductionMapper;
import com.tarapaca.api.product.infrastructure.helper.mapper.ProductBasicMapper;
import com.tarapaca.api.product_order.domain.model.ProductOrder;
import com.tarapaca.api.product_order.infrastructure.repository.ProductOrderData;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class ProductOrderMapper extends GenericProductionMapper<ProductOrder, ProductOrderData> {
    private final MeasureDetailMapper measureDetailMapper;
    private final ProductBasicMapper productMapper;
    private final StatusMapper statusMapper;
    private final MetricUnitMapper metricUnitMapper;

    public ProductOrderMapper(MeasureDetailMapper measureDetailMapper,
            ProductBasicMapper productMapper,
            UserBasicMapper userMapper,
            StatusMapper statusMapper,
            MetricUnitMapper metricUnitMapper) {
        super(ProductOrder.class, ProductOrderData.class, userMapper, statusMapper);
        this.measureDetailMapper = measureDetailMapper;
        this.productMapper = productMapper;
        this.statusMapper = statusMapper;
        this.metricUnitMapper = metricUnitMapper;
    }

    @Override
    @Nullable
    public ProductOrder toDomain(@Nullable ProductOrderData source, @NonNull ProductOrder target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getProduct()).map(productMapper::toDomain)
                .ifPresent(target::setProduct);
        Optional.ofNullable(source.getAmount()).ifPresent(target::setAmount);
        Optional.ofNullable(source.getMeasure1()).ifPresent(target::setMeasure1);
        Optional.ofNullable(source.getMeasure2()).ifPresent(target::setMeasure2);
        Optional.ofNullable(source.getMetricUnit()).map(metricUnitMapper::toDomain)
                .ifPresent(target::setMetricUnit);
        Optional.ofNullable(source.getMeasureDetail()).map(measureDetailMapper::toDomain)
                .ifPresent(target::setMeasureDetail);
        Optional.ofNullable(source.getDeliveryOrderId()).ifPresent(target::setDeliveryOrderId);
        Optional.ofNullable(source.getStatus()).map(statusMapper::toDomain)
                .ifPresent(target::setStatus);
        Optional.ofNullable(source.getObservation()).ifPresent(target::setObservation);

        return target;
    }

    @Override
    @Nullable
    public ProductOrderData toData(@Nullable ProductOrder source, @NonNull ProductOrderData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getProduct()).map(productMapper::toData)
                .ifPresent(target::setProduct);
        Optional.ofNullable(source.getAmount()).ifPresent(target::setAmount);
        Optional.ofNullable(source.getMeasure1()).ifPresent(target::setMeasure1);
        Optional.ofNullable(source.getMeasure2()).ifPresent(target::setMeasure2);
        Optional.ofNullable(source.getMetricUnit()).map(metricUnitMapper::toData)
                .ifPresent(target::setMetricUnit);
        Optional.ofNullable(source.getMeasureDetail()).map(measureDetailMapper::toData)
                .ifPresent(target::setMeasureDetail);
        Optional.ofNullable(source.getDeliveryOrderId()).ifPresent(target::setDeliveryOrderId);
        Optional.ofNullable(source.getStatus()).map(statusMapper::toData).ifPresent(target::setStatus);
        Optional.ofNullable(source.getObservation()).ifPresent(target::setObservation);

        return target;
    }
}
