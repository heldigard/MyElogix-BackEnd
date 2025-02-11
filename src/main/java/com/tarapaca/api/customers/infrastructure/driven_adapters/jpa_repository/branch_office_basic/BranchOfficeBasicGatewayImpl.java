package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office_basic;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.Session;

import com.tarapaca.api.customers.domain.model.BranchOfficeBasic;
import com.tarapaca.api.customers.domain.model.gateways.BranchOfficeBasicGateway;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.BranchOfficeBasicMapper;
import com.tarapaca.api.generics.infrastructure.repository.GenericBasic.GenericBasicGatewayImpl;

import jakarta.persistence.EntityManager;

public class BranchOfficeBasicGatewayImpl
        extends
        GenericBasicGatewayImpl<BranchOfficeBasic, BranchOfficeBasicData, BranchOfficeBasicDataJpaRepository, BranchOfficeBasicMapper>
        implements BranchOfficeBasicGateway {
    private final BranchOfficeBasicDataJpaRepository repository;
    private final BranchOfficeBasicMapper mapper;

    public BranchOfficeBasicGatewayImpl(
            BranchOfficeBasicDataJpaRepository repository,
            BranchOfficeBasicMapper mapper,
            EntityManager entityManager,
            String deletedFilter) {
        super(repository, mapper, entityManager, deletedFilter);
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public BranchOfficeBasic findByAddress(String address, boolean isDeleted) {
        Session session = setDeleteFilter(isDeleted);
        Optional<BranchOfficeBasicData> branchOfficeData = repository.findByAddress(address);
        session.disableFilter(this.deletedFilter);

        return mapper.toDomain(branchOfficeData.orElseThrow(NoSuchElementException::new));
    }
}
