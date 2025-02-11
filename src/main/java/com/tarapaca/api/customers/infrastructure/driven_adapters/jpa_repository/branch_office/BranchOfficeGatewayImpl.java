package com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.Session;

import com.tarapaca.api.customers.domain.model.BranchOffice;
import com.tarapaca.api.customers.domain.model.gateways.BranchOfficeGateway;
import com.tarapaca.api.customers.infrastructure.helpers.mappers.BranchOfficeMapper;
import com.tarapaca.api.generics.infrastructure.repository.GenericEntity.GenericGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

public class BranchOfficeGatewayImpl
        extends GenericGatewayImpl<BranchOffice, BranchOfficeData, BranchOfficeJpaRepository, BranchOfficeMapper>
        implements BranchOfficeGateway {

    public BranchOfficeGatewayImpl(
            BranchOfficeJpaRepository repository,
            BranchOfficeMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
    }

    @Override
    public BranchOffice findByAddress(String address, boolean isDeleted) {
        Optional<BranchOfficeData> branchOfficeData;
        try (Session session = setDeleteFilter(isDeleted)) {
            branchOfficeData = repository.findByAddress(address);
            session.disableFilter(this.deletedFilter);
        }

        return mapper.toDomain(branchOfficeData.orElseThrow(NoSuchElementException::new));
    }
}
