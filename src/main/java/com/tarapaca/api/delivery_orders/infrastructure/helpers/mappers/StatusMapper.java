package com.tarapaca.api.delivery_orders.infrastructure.helpers.mappers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.tarapaca.api.delivery_orders.domain.model.Status;
import com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status.StatusData;
import com.tarapaca.api.generics.infrastructure.helpers.GenericBasicMapper;

@Component
public class StatusMapper extends GenericBasicMapper<Status, StatusData> {
    public StatusMapper() {
        super(Status.class, StatusData.class);
    }

    @Override
    public Status toDomain(@Nullable StatusData source, @NonNull Status target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);
        Optional.ofNullable(source.getName()).ifPresent(target::setName);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        return target;
    }

    @Override
    public StatusData toData(@Nullable Status source, @NonNull StatusData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);
        Optional.ofNullable(source.getName()).ifPresent(target::setName);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        return target;
    }
}
