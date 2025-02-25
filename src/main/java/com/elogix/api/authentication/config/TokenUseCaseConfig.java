package com.elogix.api.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.authentication.domain.model.TokenModel;
import com.elogix.api.authentication.domain.usecase.TokenUseCase;
import com.elogix.api.authentication.infrastructure.helpers.TokenMapper;
import com.elogix.api.authentication.infrastructure.repository.JwtTokenProvider;
import com.elogix.api.authentication.infrastructure.repository.token.TokenData;
import com.elogix.api.authentication.infrastructure.repository.token.TokenDataJpaRepository;
import com.elogix.api.authentication.infrastructure.repository.token.TokenGatewayImpl;
import com.elogix.api.authentication.infrastructure.repository.userdetails.UserDetailsServiceImpl;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class TokenUseCaseConfig {
    public static final String DELETED_FILTER = "deletedTokenFilter";

    @Bean
    public TokenGatewayImpl tokenGatewayImpl(
            TokenDataJpaRepository repository,
            TokenMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            JwtTokenProvider jwtTokenProvider,
            UserDetailsServiceImpl userDetailsService) {

        GenericBasicConfig<TokenModel, TokenData, TokenDataJpaRepository, TokenMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<TokenModel, TokenData, TokenDataJpaRepository, TokenMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new TokenGatewayImpl(genericConfig, jwtTokenProvider, userDetailsService);
    }

    private GenericBasicConfig<TokenModel, TokenData, TokenDataJpaRepository, TokenMapper> createBasicConfig(
            TokenDataJpaRepository repository,
            TokenMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public TokenUseCase tokenUseCase(TokenGatewayImpl gateway) {
        return new TokenUseCase(gateway);
    }
}
