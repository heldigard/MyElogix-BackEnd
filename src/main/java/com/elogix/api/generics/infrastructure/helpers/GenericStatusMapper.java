package com.elogix.api.generics.infrastructure.helpers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.elogix.api.delivery_orders.infrastructure.helpers.mappers.StatusMapper;
import com.elogix.api.generics.domain.model.GenericStatus;
import com.elogix.api.generics.infrastructure.repository.GenericStatus.GenericStatusData;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;

public class GenericStatusMapper<D extends GenericStatus, E extends GenericStatusData>
        extends GenericEntityMapper<D, E> {

    private final StatusMapper statusMapper;

    protected GenericStatusMapper(@NonNull Class<D> domainClass, @NonNull Class<E> dataClass,
            @NonNull UserBasicMapper userMapper, @NonNull StatusMapper statusMapper) {
        super(domainClass, dataClass, userMapper);
        this.statusMapper = statusMapper;
    }

    @Override
    @Nullable
    public D toDomain(@Nullable E source, @NonNull D target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);
        Optional.ofNullable(source.getStatus()).map(statusMapper::toDomain).ifPresent(target::setStatus);

        return target;
    }

    @Override
    @Nullable
    public E toData(@Nullable D source, @NonNull E target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);
        Optional.ofNullable(source.getStatus()).map(statusMapper::toData).ifPresent(target::setStatus);

        return target;
    }
}
