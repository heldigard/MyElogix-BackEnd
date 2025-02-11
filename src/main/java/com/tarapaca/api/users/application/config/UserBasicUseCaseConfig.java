package com.tarapaca.api.users.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.users.domain.model.gateways.UserBasicGateway;
import com.tarapaca.api.users.domain.usecase.UserBasicUseCase;
import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.user_basic.UserBasicDataJpaRepository;
import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.user_basic.UserBasicGatewayImpl;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserBasicMapper;

import jakarta.persistence.EntityManager;

@Configuration
public class UserBasicUseCaseConfig {
    @Bean
    public UserBasicGatewayImpl userBasicGatewayImpl(UserBasicMapper mapper, UserBasicDataJpaRepository repository,
            EntityManager entityManager) {
        return new UserBasicGatewayImpl(mapper, repository, entityManager, "deletedUserBasicFilter");
    }

    @Bean
    public UserBasicUseCase userBasicUseCase(UserBasicGateway gateway) {
        return new UserBasicUseCase(gateway);
    }
}
