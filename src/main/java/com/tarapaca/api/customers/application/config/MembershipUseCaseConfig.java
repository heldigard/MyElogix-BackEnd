package com.tarapaca.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tarapaca.api.customers.domain.model.gateways.MembershipGateway;
import com.tarapaca.api.customers.domain.usecase.MembershipUseCase;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipDataJpaRepository;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipGatewayImpl;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.MembershipMapper;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class MembershipUseCaseConfig {
    @Bean
    public MembershipGatewayImpl membershipGatewayImpl(
            MembershipDataJpaRepository repository,
            MembershipMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {
        return new MembershipGatewayImpl(
                repository,
                mapper,
                entityManager,
                updateUtils,
                "deletedMembershipFilter");
    }

    @Bean
    public MembershipUseCase membershipUseCase(MembershipGateway gateway) {
        return new MembershipUseCase(gateway);
    }
}
