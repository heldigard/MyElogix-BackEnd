package com.tarapaca.api.users.infrastructure.helpers.mappers;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.tarapaca.api.generics.infrastructure.helpers.GenericEntityMapper;
import com.tarapaca.api.users.domain.model.RoleModel;
import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.role.RoleData;

import lombok.Getter;

@Component
@Getter
public class RoleMapper extends GenericEntityMapper<RoleModel, RoleData> {

    public RoleMapper(UserBasicMapper userMapper) {
        super(RoleModel.class, RoleData.class, userMapper);
    }

    @Override
    @Nullable
    public RoleModel toDomain(@Nullable RoleData source, @NonNull RoleModel target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getName()).ifPresent(target::setName);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        Optional.of(source.isActive()).ifPresent(target::setActive);

        return target;
    }

    @Override
    @Nullable
    public RoleData toData(@Nullable RoleModel source, @NonNull RoleData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getName()).ifPresent(target::setName);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        Optional.of(source.isActive()).ifPresent(target::setActive);

        return target;
    }

    public Set<String> toStringList(Set<RoleModel> roles) {
        return Optional.ofNullable(roles)
                .map(roleSet -> roleSet.stream()
                        .map(role -> role.getName().name())
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }
}
