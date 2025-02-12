package com.elogix.api.delivery_orders.infrastructure.helpers.mappers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.elogix.api.delivery_orders.domain.model.MeasureDetail;
import com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.measure_detail.MeasureDetailData;
import com.elogix.api.generics.infrastructure.helpers.GenericNamedMapper;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class MeasureDetailMapper extends GenericNamedMapper<MeasureDetail, MeasureDetailData> {

    public MeasureDetailMapper(UserBasicMapper userMapper) {
        super(MeasureDetail.class, MeasureDetailData.class, userMapper);
    }

    @Override
    @Nullable
    public MeasureDetail toDomain(@Nullable MeasureDetailData source, @NonNull MeasureDetail target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        return target;
    }

    @Override
    @Nullable
    public MeasureDetailData toData(@Nullable MeasureDetail source, @NonNull MeasureDetailData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        return target;
    }
}
