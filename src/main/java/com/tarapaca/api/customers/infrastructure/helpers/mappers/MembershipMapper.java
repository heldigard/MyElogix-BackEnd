package com.tarapaca.api.customers.infrastructure.helpers.mappers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.tarapaca.api.customers.domain.model.Membership;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipData;
import com.tarapaca.api.generics.infrastructure.helpers.GenericEntityMapper;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class MembershipMapper extends GenericEntityMapper<Membership, MembershipData> {

    public MembershipMapper(UserBasicMapper userMapper) {
        super(Membership.class, MembershipData.class, userMapper);
    }

    @Override
    @Nullable
    public Membership toDomain(@Nullable MembershipData source, @NonNull Membership target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getName()).ifPresent(target::setName);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        target.setDeleted(source.isDeleted());

        return target;
    }

    @Override
    @Nullable
    public MembershipData toData(@Nullable Membership source, @NonNull MembershipData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getName()).ifPresent(target::setName);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        target.setDeleted(source.isDeleted());

        return target;
    }
}
