package com.elogix.api.users.infrastructure.helpers.mappers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.elogix.api.authentication.domain.model.UserDetailsImpl;
import com.elogix.api.generics.infrastructure.helpers.GenericBasicMapper;
import com.elogix.api.users.domain.model.UserBasic;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user_basic.UserBasicData;

@Component
public class UserBasicMapper extends GenericBasicMapper<UserBasic, UserBasicData> {
    public UserBasicMapper() {
        super(UserBasic.class, UserBasicData.class);
    }

    @Override
    @Nullable
    public UserBasic toDomain(@Nullable UserBasicData source, @NonNull UserBasic target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getUsername()).ifPresent(target::setUsername);
        Optional.ofNullable(source.getFirstName()).ifPresent(target::setFirstName);
        Optional.ofNullable(source.getLastName()).ifPresent(target::setLastName);

        Optional.of(source.isActive()).ifPresent(target::setActive);
        Optional.of(source.isLocked()).ifPresent(target::setLocked);

        return target;
    }

    @Override
    @Nullable
    public UserBasicData toData(@Nullable UserBasic source, @NonNull UserBasicData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getUsername()).ifPresent(target::setUsername);
        Optional.ofNullable(source.getFirstName()).ifPresent(target::setFirstName);
        Optional.ofNullable(source.getLastName()).ifPresent(target::setLastName);

        Optional.of(source.isActive()).ifPresent(target::setActive);
        Optional.of(source.isLocked()).ifPresent(target::setLocked);

        return target;
    }

    public UserDetailsImpl toUserDetails(UserBasic userBasic) {
        Optional.ofNullable(userBasic)
                .orElseThrow(() -> new IllegalArgumentException("user is null"));

        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setId(userBasic.getId());
        userDetails.setUsername(userBasic.getUsername());
        userDetails.setFirstName(userBasic.getFirstName());
        userDetails.setLastName(userBasic.getLastName());

        userDetails.setActive(userBasic.isActive());
        userDetails.setLocked(userBasic.isLocked());
        userDetails.setDeleted(userBasic.isDeleted());

        return userDetails;
    }

    public UserBasic fromUserDetails(UserDetailsImpl userDetails) {
        Optional.ofNullable(userDetails)
                .orElseThrow(() -> new IllegalArgumentException("user is null"));

        UserBasic userBasic = new UserBasic();
        userBasic.setId(userDetails.getId());
        userBasic.setUsername(userDetails.getUsername());
        userBasic.setFirstName(userDetails.getFirstName());
        userBasic.setLastName(userDetails.getLastName());

        userBasic.setActive(userDetails.isActive());
        userBasic.setLocked(userDetails.isLocked());
        userBasic.setDeleted(userDetails.isDeleted());

        return userBasic;
    }
}
