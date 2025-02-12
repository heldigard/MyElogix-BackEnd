package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer_basic;

import org.hibernate.Session;

import com.elogix.api.customers.domain.model.CustomerBasic;
import com.elogix.api.customers.domain.model.gateways.CustomerBasicGateway;
import com.elogix.api.customers.infrastructure.helpers.mappers.CustomerBasicMapper;
import com.elogix.api.generics.infrastructure.repository.GenericNamedBasic.GenericNamedBasicGatewayImpl;

import jakarta.persistence.EntityManager;

public class CustomerBasicGatewayImpl
        extends
        GenericNamedBasicGatewayImpl<CustomerBasic, CustomerBasicData, CustomerBasicDataJpaRepository, CustomerBasicMapper>
        implements CustomerBasicGateway {
    private final CustomerBasicDataJpaRepository repository;
    private final CustomerBasicMapper mapper;

    public CustomerBasicGatewayImpl(
            CustomerBasicDataJpaRepository repository,
            CustomerBasicMapper mapper,
            EntityManager entityManager,
            String deletedFilter) {
        super(repository, mapper, entityManager, deletedFilter);
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public CustomerBasic findByEmail(String email, boolean isDeleted) {
        Session session = setDeleteFilter(isDeleted);
        var customerBasicData = repository.findByEmail(email);
        session.disableFilter(this.deletedFilter);

        return customerBasicData
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public CustomerBasic findByDocumentNumber(String documentNumber, boolean isDeleted) {
        Session session = setDeleteFilter(isDeleted);
        var customerBasicData = repository.findByDocumentNumber(documentNumber);
        session.disableFilter(this.deletedFilter);

        return customerBasicData
                .map(mapper::toDomain)
                .orElse(null);
    }
}
