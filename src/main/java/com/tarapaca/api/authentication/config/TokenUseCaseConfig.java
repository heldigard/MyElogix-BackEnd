package com.tarapaca.api.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.authentication.domain.usecase.TokenUseCase;
import com.tarapaca.api.authentication.infrastructure.helpers.TokenMapper;
import com.tarapaca.api.authentication.infrastructure.repository.JwtTokenProvider;
import com.tarapaca.api.authentication.infrastructure.repository.token.TokenDataJpaRepository;
import com.tarapaca.api.authentication.infrastructure.repository.token.TokenGatewayImpl;
import com.tarapaca.api.authentication.infrastructure.repository.userdetails.UserDetailsServiceImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class TokenUseCaseConfig {
    @Bean
    public TokenGatewayImpl tokenGatewayImpl(
            TokenDataJpaRepository repository,
            TokenMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            JwtTokenProvider jwtTokenProvider,
            UserDetailsServiceImpl userDetailsService) {
        return new TokenGatewayImpl(
                repository,
                mapper,
                entityManager,
                updateUtils,
                jwtTokenProvider,
                userDetailsService);
    }

    @Bean
    public TokenUseCase tokenUseCase(TokenGatewayImpl gateway) {
        return new TokenUseCase(gateway);
    }
}
