package com.elogix.api.users.infrastructure.helpers.mappers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.infrastructure.helpers.GenericNamedMapper;
import com.elogix.api.users.domain.model.Office;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.office.OfficeData;

@Component
public class OfficeMapper extends GenericNamedMapper<Office, OfficeData> {
    public OfficeMapper(UserBasicMapper userMapper) {
        super(Office.class, OfficeData.class, userMapper);
    }

    @Override
    @Nullable
    public Office toDomain(@Nullable OfficeData source, @NonNull Office target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getAddress()).ifPresent(target::setAddress);

        return target;
    }

    @Override
    @Nullable
    public OfficeData toData(@Nullable Office source, @NonNull OfficeData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getAddress()).ifPresent(target::setAddress);

        return target;
    }
}
