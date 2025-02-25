package com.elogix.api.users.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.users.domain.model.UserModel;
import com.elogix.api.users.domain.model.gateways.UserGateway;
import com.elogix.api.users.domain.usecase.UserUseCase;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user.UserData;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user.UserDataJpaRepository;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user.UserGatewayImpl;
import com.elogix.api.users.infrastructure.helpers.mappers.RoleMapper;
import com.elogix.api.users.infrastructure.helpers.mappers.UserMapper;

import jakarta.persistence.EntityManager;

@Configuration
public class UserUseCaseConfig {
    public static final String DELETED_FILTER = "deletedUserFilter";

    @Bean
    public UserGatewayImpl userGatewayImpl(
            UserDataJpaRepository repository,
            UserMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            RoleMapper roleMapper,
            PasswordEncoder passwordEncoder) {

        GenericBasicConfig<UserModel, UserData, UserDataJpaRepository, UserMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<UserModel, UserData, UserDataJpaRepository, UserMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new UserGatewayImpl(genericConfig, roleMapper, passwordEncoder);
    }

    private GenericBasicConfig<UserModel, UserData, UserDataJpaRepository, UserMapper> createBasicConfig(
            UserDataJpaRepository repository,
            UserMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public UserUseCase userUseCase(UserGateway gateway) {
        return new UserUseCase(gateway);
    }
}
