package com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user;

import java.util.Optional;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.users.domain.model.RoleModel;
import com.elogix.api.users.domain.model.UserModel;
import com.elogix.api.users.domain.model.gateways.UserGateway;
import com.elogix.api.users.infrastructure.helpers.mappers.RoleMapper;
import com.elogix.api.users.infrastructure.helpers.mappers.UserMapper;

import jakarta.persistence.EntityManager;

public class UserGatewayImpl extends GenericGatewayImpl<UserModel, UserData, UserDataJpaRepository, UserMapper>
        implements UserGateway {
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;

    private static final String ENCODED_PASSWORD_PREFIX = "$2a$10$";
    private static final int MIN_PASSWORD_LENGTH = 6;

    public UserGatewayImpl(
            UserMapper mapper,
            RoleMapper roleMapper,
            UserDataJpaRepository repository,
            PasswordEncoder passwordEncoder,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel add(UserModel userModel) {
        validateUsername(userModel.getUsername());
        validateAndEncodePassword(userModel);

        UserData userData = mapper.toData(userModel);
        UserData userDataSaved = repository.save(userData);
        return mapper.toDomain(userDataSaved);
    }

    private void validateUsername(String username) {
        if (repository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username '" + username + "' already exists");
        }
    }

    private void validateAndEncodePassword(UserModel userModel) {
        String password = userModel.getPassword();
        if (!password.startsWith(ENCODED_PASSWORD_PREFIX) && !password.isEmpty()) {
            if (password.length() > MIN_PASSWORD_LENGTH) {
                userModel.setPassword(passwordEncoder.encode(password));
            } else {
                throw new IllegalArgumentException(
                        "Password must be at least " + MIN_PASSWORD_LENGTH + " characters long");
            }
        }
    }

    @Override
    public UserModel update(UserModel user) {
        validateAndEncodePassword(user);

        UserModel existing = findById(user.getId(), false);
        updateUtils.updateIfNotEmptyAndNotEqual(user.getPassword(), existing::setPassword,
                existing.getPassword());
        updateUtils.updateIfNotEmptyAndNotEqual(user.getRoles(), existing::setRoles,
                existing.getRoles());
        updateUtils.updateIfNotEmptyAndNotEqual(user.getFirstName().toUpperCase(),
                existing::setFirstName, existing.getFirstName());
        updateUtils.updateIfNotEmptyAndNotEqual(user.getLastName().toUpperCase(),
                existing::setLastName, existing.getLastName());
        updateUtils.updateIfNotEmptyAndNotEqual(user.getEmail().toLowerCase(), existing::setEmail,
                existing.getEmail());
        updateUtils.updateIfNotEmptyAndNotEqual(user.getUsername().toLowerCase(),
                existing::setUsername, existing.getUsername());
        updateUtils.updateIfNotEmptyAndNotEqual(user.getPhone(), existing::setPhone,
                existing.getPhone());
        updateUtils.updateIfNotEmptyAndNotEqual(user.isActive(), existing::setActive,
                existing.isActive());
        updateUtils.updateIfNotEmptyAndNotEqual(user.isLocked(), existing::setLocked,
                existing.isLocked());

        return save(existing);
    }

    @Override
    public int updateRole(Long id, Set<RoleModel> roleModels) {
        return repository.updateRole(id, roleMapper.toData(roleModels));
    }

    @Override
    public void deleteByEmail(String email) {
        repository.deleteByEmail(email);
    }

    @Override
    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }

    @Override
    public UserModel findByEmail(String email, boolean isDeleted) {
        Optional<UserData> userData;
        try (Session session = setDeleteFilter(isDeleted)) {
            userData = repository.findByEmail(email);
            session.disableFilter(this.deletedFilter);
        }

        return mapper.toDomain(userData.orElse(null));
    }

    @Override
    public UserModel findByUsername(String username, boolean isDeleted) {
        Optional<UserData> userData;
        try (Session session = setDeleteFilter(isDeleted)) {
            userData = repository.findByUsername(username);
            session.disableFilter(this.deletedFilter);
        }

        return mapper.toDomain(userData.orElse(null));
    }
}
