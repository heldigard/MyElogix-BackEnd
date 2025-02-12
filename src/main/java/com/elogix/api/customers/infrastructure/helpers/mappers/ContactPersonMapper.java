package com.elogix.api.customers.infrastructure.helpers.mappers;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.ContactPerson;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person.ContactPersonData;
import com.elogix.api.generics.infrastructure.helpers.GenericNamedMapper;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class ContactPersonMapper extends GenericNamedMapper<ContactPerson, ContactPersonData> {

    public ContactPersonMapper(UserBasicMapper userMapper) {
        super(ContactPerson.class, ContactPersonData.class, userMapper);
    }

    @Override
    @Nullable
    public ContactPerson toDomain(@Nullable ContactPersonData source, @NonNull ContactPerson target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getMobileNumberPrimary()).ifPresent(target::setMobileNumberPrimary);
        Optional.ofNullable(source.getMobileNumberSecondary()).ifPresent(target::setMobileNumberSecondary);

        Set<Long> branchOffices = Optional.ofNullable(source.getBranchOfficeList())
                .orElse(Collections.emptySet())
                .stream()
                .map(BranchOfficeData::getId)
                .collect(Collectors.toSet());
        target.setBranchOfficeIdList(branchOffices);

        return target;
    }

    @Override
    @Nullable
    public ContactPersonData toData(@Nullable ContactPerson source, @NonNull ContactPersonData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getMobileNumberPrimary()).ifPresent(target::setMobileNumberPrimary);
        Optional.ofNullable(source.getMobileNumberSecondary()).ifPresent(target::setMobileNumberSecondary);

        Set<BranchOfficeData> branchOffices = Optional.ofNullable(source.getBranchOfficeIdList())
                .orElse(Collections.emptySet())
                .stream()
                .map(id -> {
                    BranchOfficeData data = new BranchOfficeData();
                    data.setId(id);
                    return data;
                })
                .collect(Collectors.toSet());
        target.setBranchOfficeList(branchOffices);

        return target;
    }
}
