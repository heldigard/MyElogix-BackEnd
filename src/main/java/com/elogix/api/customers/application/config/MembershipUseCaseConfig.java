package com.elogix.api.customers.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elogix.api.customers.domain.model.Membership;
import com.elogix.api.customers.domain.model.gateways.MembershipGateway;
import com.elogix.api.customers.domain.usecase.MembershipUseCase;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipGatewayImpl;
import com.elogix.api.customers.infrastructure.helpers.mappers.MembershipMapper;
import com.elogix.api.generics.config.GenericBasicConfig;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

@Configuration
public class MembershipUseCaseConfig {
    public static final String DELETED_FILTER = "deletedMembershipFilter";

    @Bean
    public MembershipGatewayImpl membershipGatewayImpl(
            MembershipDataJpaRepository repository,
            MembershipMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils) {

        GenericBasicConfig<Membership, MembershipData, MembershipDataJpaRepository, MembershipMapper> basicConfig = createBasicConfig(
                repository, mapper, entityManager);

        GenericConfig<Membership, MembershipData, MembershipDataJpaRepository, MembershipMapper> genericConfig = new GenericConfig<>(
                basicConfig, updateUtils);

        return new MembershipGatewayImpl(genericConfig);
    }

    private GenericBasicConfig<Membership, MembershipData, MembershipDataJpaRepository, MembershipMapper> createBasicConfig(
            MembershipDataJpaRepository repository,
            MembershipMapper mapper,
            EntityManager entityManager) {
        return new GenericBasicConfig<>(
                repository,
                mapper,
                entityManager,
                DELETED_FILTER);
    }

    @Bean
    public MembershipUseCase membershipUseCase(MembershipGateway gateway) {
        return new MembershipUseCase(gateway);
    }
}
