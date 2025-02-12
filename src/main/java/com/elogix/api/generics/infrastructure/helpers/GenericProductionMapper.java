package com.elogix.api.generics.infrastructure.helpers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.elogix.api.delivery_orders.infrastructure.helpers.mappers.StatusMapper;
import com.elogix.api.generics.domain.model.GenericProduction;
import com.elogix.api.generics.infrastructure.repository.GenericProduction.GenericProductionData;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;

public class GenericProductionMapper<D extends GenericProduction, E extends GenericProductionData>
        extends GenericStatusMapper<D, E> {

    private final UserBasicMapper userMapper;

    protected GenericProductionMapper(@NonNull Class<D> domainClass, @NonNull Class<E> dataClass,
            @NonNull UserBasicMapper userMapper, @NonNull StatusMapper statusMapper) {
        super(domainClass, dataClass, userMapper, statusMapper);
        this.userMapper = userMapper;
    }

    @Override
    @Nullable
    public D toDomain(@Nullable E source, @NonNull D target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);
        Optional.ofNullable(source.getProductionAt()).ifPresent(target::setProductionAt);
        Optional.ofNullable(source.getFinishedAt()).ifPresent(target::setFinishedAt);
        Optional.ofNullable(source.getDeliveredAt()).ifPresent(target::setDeliveredAt);
        Optional.ofNullable(source.getCancelledAt()).ifPresent(target::setCancelledAt);
        Optional.ofNullable(source.getPausedAt()).ifPresent(target::setPausedAt);

        Optional.ofNullable(source.getProductionBy()).map(userMapper::toDomain).ifPresent(target::setProductionBy);
        Optional.ofNullable(source.getFinishedBy()).map(userMapper::toDomain).ifPresent(target::setFinishedBy);
        Optional.ofNullable(source.getDeliveredBy()).map(userMapper::toDomain).ifPresent(target::setDeliveredBy);
        Optional.ofNullable(source.getCancelledBy()).map(userMapper::toDomain).ifPresent(target::setCancelledBy);
        Optional.ofNullable(source.getPausedBy()).map(userMapper::toDomain).ifPresent(target::setPausedBy);

        target.setPending(source.isPending());
        target.setProduction(source.isProduction());
        target.setFinished(source.isFinished());
        target.setDelivered(source.isDelivered());
        target.setCancelled(source.isCancelled());
        target.setPaused(source.isPaused());

        return target;
    }

    @Override
    @Nullable
    public E toData(@Nullable D source, @NonNull E target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);
        Optional.ofNullable(source.getProductionAt()).ifPresent(target::setProductionAt);
        Optional.ofNullable(source.getFinishedAt()).ifPresent(target::setFinishedAt);
        Optional.ofNullable(source.getDeliveredAt()).ifPresent(target::setDeliveredAt);
        Optional.ofNullable(source.getCancelledAt()).ifPresent(target::setCancelledAt);
        Optional.ofNullable(source.getPausedAt()).ifPresent(target::setPausedAt);

        Optional.ofNullable(source.getProductionBy()).map(userMapper::toData).ifPresent(target::setProductionBy);
        Optional.ofNullable(source.getFinishedBy()).map(userMapper::toData).ifPresent(target::setFinishedBy);
        Optional.ofNullable(source.getDeliveredBy()).map(userMapper::toData).ifPresent(target::setDeliveredBy);
        Optional.ofNullable(source.getCancelledBy()).map(userMapper::toData).ifPresent(target::setCancelledBy);
        Optional.ofNullable(source.getPausedBy()).map(userMapper::toData).ifPresent(target::setPausedBy);

        target.setPending(source.isPending());
        target.setProduction(source.isProduction());
        target.setFinished(source.isFinished());
        target.setDelivered(source.isDelivered());
        target.setCancelled(source.isCancelled());
        target.setPaused(source.isPaused());

        return target;
    }
}
