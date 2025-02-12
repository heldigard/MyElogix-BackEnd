package com.elogix.api.customers.infrastructure.helpers.mappers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.CustomerBasic;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer_basic.CustomerBasicData;
import com.elogix.api.generics.infrastructure.helpers.GenericNamedBasicMapper;

@Component
public class CustomerBasicMapper extends GenericNamedBasicMapper<CustomerBasic, CustomerBasicData> {

    private final MembershipMapper membershipMapper;

    public CustomerBasicMapper(MembershipMapper membershipMapper) {
        super(CustomerBasic.class, CustomerBasicData.class);
        this.membershipMapper = membershipMapper;
    }

    @Override
    @Nullable
    public CustomerBasic toDomain(@Nullable CustomerBasicData source, @NonNull CustomerBasic target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getDocumentNumber()).ifPresent(target::setDocumentNumber);
        Optional.ofNullable(source.getEmail()).ifPresent(target::setEmail);
        Optional.ofNullable(source.getPhone()).ifPresent(target::setPhone);
        Optional.ofNullable(source.getHits()).ifPresent(target::setHits);
        target.setActive(source.isActive());

        Optional.ofNullable(source.getMembership())
                .ifPresent(membership -> target.setMembership(membershipMapper.toDomain(membership)));

        return target;
    }

    @Override
    @Nullable
    public CustomerBasicData toData(@Nullable CustomerBasic source, @NonNull CustomerBasicData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getDocumentNumber()).ifPresent(target::setDocumentNumber);
        Optional.ofNullable(source.getEmail()).ifPresent(target::setEmail);
        Optional.ofNullable(source.getPhone()).ifPresent(target::setPhone);
        Optional.ofNullable(source.getHits()).ifPresent(target::setHits);
        target.setActive(source.isActive());

        Optional.ofNullable(source.getMembership())
                .ifPresent(membership -> target.setMembership(membershipMapper.toData(membership)));

        return target;
    }
}
