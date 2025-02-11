package com.tarapaca.api.customers.infrastructure.helpers.mappers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.tarapaca.api.customers.domain.model.BranchOfficeBasic;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office_basic.BranchOfficeBasicData;
import com.tarapaca.api.generics.infrastructure.helpers.GenericBasicMapper;

@Component
public class BranchOfficeBasicMapper extends GenericBasicMapper<BranchOfficeBasic, BranchOfficeBasicData> {

    public BranchOfficeBasicMapper() {
        super(BranchOfficeBasic.class, BranchOfficeBasicData.class);
    }

    @Override
    @Nullable
    public BranchOfficeBasic toDomain(@Nullable BranchOfficeBasicData source, @NonNull BranchOfficeBasic target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getAddress()).ifPresent(target::setAddress);
        Optional.ofNullable(source.getCustomerId()).ifPresent(target::setCustomerId);

        return target;
    }

    @Override
    @Nullable
    public BranchOfficeBasicData toData(@Nullable BranchOfficeBasic source, @NonNull BranchOfficeBasicData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getAddress()).ifPresent(target::setAddress);
        Optional.ofNullable(source.getCustomerId()).ifPresent(target::setCustomerId);

        return target;
    }
}
