package com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.Session;

import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.delivery_orders.domain.model.gateways.StatusGateway;
import com.elogix.api.delivery_orders.infrastructure.helpers.mappers.StatusMapper;
import com.elogix.api.generics.infrastructure.repository.GenericBasic.GenericBasicGatewayImpl;

import jakarta.persistence.EntityManager;

public class StatusGatewayImpl
        extends GenericBasicGatewayImpl<Status, StatusData, StatusDataJpaRepository, StatusMapper>
        implements StatusGateway {

    public StatusGatewayImpl(
            StatusDataJpaRepository repository,
            StatusMapper mapper,
            EntityManager entityManager,
            String deletedFilter) {
        super(repository, mapper, entityManager, deletedFilter);
    }

    @Override
    public Status findByName(EStatus name, boolean isDeleted) {
        Session session = setDeleteFilter(isDeleted);
        try {
            Optional<StatusData> statusData = repository.findByName(name);
            return mapper.toDomain(statusData.orElseThrow(NoSuchElementException::new));
        } finally {
            session.close();
        }
    }

    @Override
    public List<Status> findByNameIn(List<EStatus> names, boolean isDeleted) {
        Session session = setDeleteFilter(isDeleted);
        try {
            List<StatusData> statusDataList = repository.findByNameIn(names);
            List<Status> statusList = mapper.toDomain(statusDataList).stream().toList();
            return statusList;
        } finally {
            session.close();
        }
    }
}
