package com.elogix.api.users.infrastructure.helpers.mappers;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.elogix.api.authentication.domain.model.UserDetailsImpl;
import com.elogix.api.generics.infrastructure.helpers.GenericEntityMapper;
import com.elogix.api.shared.infraestructure.dto.UserDTO;
import com.elogix.api.users.domain.model.RoleModel;
import com.elogix.api.users.domain.model.UserModel;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.role.RoleData;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user.UserData;

import lombok.Getter;

@Component
@Getter
public class UserMapper extends GenericEntityMapper<UserModel, UserData> {
    private final RoleMapper roleMapper;
    private final OfficeMapper officeMapper;

    public UserMapper(UserBasicMapper userBasicMapper, RoleMapper roleMapper, OfficeMapper officeMapper) {
        super(UserModel.class, UserData.class, userBasicMapper);
        this.roleMapper = roleMapper;
        this.officeMapper = officeMapper;
    }

    @Override
    @Nullable
    public UserModel toDomain(@Nullable UserData source, @NonNull UserModel target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getUsername()).ifPresent(target::setUsername);
        Optional.ofNullable(source.getPassword()).ifPresent(target::setPassword);
        Optional.ofNullable(source.getEmail()).ifPresent(target::setEmail);
        Optional.ofNullable(source.getFirstName()).ifPresent(target::setFirstName);
        Optional.ofNullable(source.getLastName()).ifPresent(target::setLastName);
        Optional.ofNullable(source.getPhone()).ifPresent(target::setPhone);
        Optional.ofNullable(source.getOffice()).ifPresent(office -> target.setOffice(officeMapper.toDomain(office)));
        Optional.of(source.isActive()).ifPresent(target::setActive);
        Optional.of(source.isLocked()).ifPresent(target::setLocked);

        if (source.getRoles() != null) {
            Set<RoleModel> roles = source.getRoles().stream()
                    .map(roleMapper::toDomain)
                    .collect(Collectors.toSet());
            target.setRoles(roles);
        }

        return target;
    }

    @Override
    @Nullable
    public UserData toData(@Nullable UserModel source, @NonNull UserData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getUsername()).ifPresent(target::setUsername);
        Optional.ofNullable(source.getPassword()).ifPresent(target::setPassword);
        Optional.ofNullable(source.getEmail()).ifPresent(target::setEmail);
        Optional.ofNullable(source.getFirstName()).ifPresent(target::setFirstName);
        Optional.ofNullable(source.getLastName()).ifPresent(target::setLastName);
        Optional.ofNullable(source.getPhone()).ifPresent(target::setPhone);
        Optional.ofNullable(source.getOffice()).ifPresent(office -> target.setOffice(officeMapper.toData(office)));
        Optional.of(source.isActive()).ifPresent(target::setActive);
        Optional.of(source.isLocked()).ifPresent(target::setLocked);

        if (source.getRoles() != null) {
            Set<RoleData> roles = source.getRoles().stream()
                    .map(roleMapper::toData)
                    .collect(Collectors.toSet());
            target.setRoles(roles);
        }

        return target;
    }

    public UserDetailsImpl toUserDetails(UserModel user) {
        validateUserModel(user);

        return UserDetailsImpl.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.isActive())
                .isLocked(user.isLocked())
                .roles(user.getRoles())
                .build();
    }

    private void validateUserModel(UserModel user) {
        Optional.ofNullable(user)
                .orElseThrow(() -> new IllegalArgumentException("user is null"));
    }

    private void validateUserDetails(UserDetailsImpl user) {
        Optional.ofNullable(user)
                .orElseThrow(() -> new IllegalArgumentException("user is null"));
    }

    public UserModel fromUserDetails(UserDetailsImpl user) {
        validateUserDetails(user);

        return UserModel.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.isActive())
                .isLocked(user.isLocked())
                .roles(user.getRoles())
                .roles(user.getRoles())
                .build();
    }

    public UserDTO toUserDTO(UserModel user) {
        validateUserModel(user);
        validateUserId(user.getId());

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.isActive())
                .isLocked(user.isLocked())
                .isDeleted(user.isDeleted())
                .roles(roleMapper.toStringList(user.getRoles()))
                .build();
    }

    private void validateUserId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }
}
