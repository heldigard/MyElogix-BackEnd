package com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.office;

import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;
import com.tarapaca.api.users.domain.model.Office;
import com.tarapaca.api.users.domain.model.gateways.OfficeGateway;
import com.tarapaca.api.users.infrastructure.helpers.mappers.OfficeMapper;

import jakarta.persistence.EntityManager;

public class OfficeGatewayImpl extends
        GenericNamedGatewayImpl<Office, OfficeData, OfficeDataJpaRepository, OfficeMapper> implements OfficeGateway {

    public OfficeGatewayImpl(OfficeDataJpaRepository repository, OfficeMapper mapper, EntityManager entityManager,
            UpdateUtils updateUtils, String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
    }
}
