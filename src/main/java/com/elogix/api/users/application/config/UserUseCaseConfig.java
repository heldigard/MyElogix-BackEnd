package com.elogix.api.users.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.users.domain.model.gateways.UserGateway;
import com.elogix.api.users.domain.usecase.UserUseCase;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user.UserDataJpaRepository;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user.UserGatewayImpl;
import com.elogix.api.users.infrastructure.helpers.mappers.RoleMapper;
import com.elogix.api.users.infrastructure.helpers.mappers.UserMapper;

import jakarta.persistence.EntityManager;

@Configuration
public class UserUseCaseConfig {
    @Bean
    public UserGatewayImpl userGatewayImpl(
            UserMapper mapper,
            RoleMapper roleMapper,
            UserDataJpaRepository repository,
            PasswordEncoder passwordEncoder,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new UserGatewayImpl(
                mapper,
                roleMapper,
                repository,
                passwordEncoder,
                entityManager,
                updateUtils,
                "deletedUserFilter");
    }

    @Bean
    public UserUseCase userUseCase(UserGateway gateway) {
        return new UserUseCase(gateway);
    }
}
