package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericEntityRepository;

@Repository
public interface BranchOfficeJpaRepository
        extends GenericEntityRepository<BranchOfficeData> {

    Optional<BranchOfficeData> findByAddress(String address);
}
