package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.Session;

import com.elogix.api.customers.domain.model.BranchOffice;
import com.elogix.api.customers.domain.model.gateways.BranchOfficeGateway;
import com.elogix.api.customers.infrastructure.helpers.mappers.BranchOfficeMapper;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericGatewayImpl;

public class BranchOfficeGatewayImpl
        extends GenericGatewayImpl<BranchOffice, BranchOfficeData, BranchOfficeDataJpaRepository, BranchOfficeMapper>
        implements BranchOfficeGateway {

    public BranchOfficeGatewayImpl(
            GenericConfig<BranchOffice, BranchOfficeData, BranchOfficeDataJpaRepository, BranchOfficeMapper> config) {
        super(config);
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
