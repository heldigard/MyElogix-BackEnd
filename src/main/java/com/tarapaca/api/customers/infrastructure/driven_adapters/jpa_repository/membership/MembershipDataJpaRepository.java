package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.membership;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tarapaca.api.customers.domain.model.EMembership;
import com.tarapaca.api.generics.infrastructure.repository.GenericEntity.GenericEntityRepository;

@Repository
public interface MembershipDataJpaRepository
        extends GenericEntityRepository<MembershipData> {
    Optional<MembershipData> findByName(EMembership name);
}
