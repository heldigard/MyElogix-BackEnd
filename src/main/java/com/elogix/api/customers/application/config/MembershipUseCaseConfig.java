package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.gateways.MembershipGateway;
import com.elogix.api.customers.domain.usecase.MembershipUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.MembershipMapper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

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
