package com.elogix.api.customers.infrastructure.helpers.mappers;

import com.elogix.api.customers.domain.model.ContactPersonBasic;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person_basic.ContactPersonBasicData;
import com.elogix.api.generics.infrastructure.helpers.GenericNamedBasicMapper;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContactPersonBasicMapper extends GenericNamedBasicMapper<ContactPersonBasic, ContactPersonBasicData> {

    public ContactPersonBasicMapper() {
        super(ContactPersonBasic.class, ContactPersonBasicData.class);
    }

    @Override
    @Nullable
    public ContactPersonBasic toDomain(@Nullable ContactPersonBasicData source, @NonNull ContactPersonBasic target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getMobileNumberPrimary()).ifPresent(target::setMobileNumberPrimary);
        Optional.ofNullable(source.getMobileNumberSecondary()).ifPresent(target::setMobileNumberSecondary);

        return target;
    }

    @Override
    @Nullable
    public ContactPersonBasicData toData(@Nullable ContactPersonBasic source, @NonNull ContactPersonBasicData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getMobileNumberPrimary()).ifPresent(target::setMobileNumberPrimary);
        Optional.ofNullable(source.getMobileNumberSecondary()).ifPresent(target::setMobileNumberSecondary);

        return target;
    }
}
