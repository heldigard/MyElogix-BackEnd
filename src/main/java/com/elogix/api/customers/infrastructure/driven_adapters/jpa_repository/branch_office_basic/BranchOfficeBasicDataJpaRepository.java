package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office_basic;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.elogix.api.generics.infrastructure.repository.GenericBasic.GenericBasicRepository;

@Repository
public interface BranchOfficeBasicDataJpaRepository
        extends GenericBasicRepository<BranchOfficeBasicData> {
    Optional<BranchOfficeBasicData> findByAddress(String address);
}
