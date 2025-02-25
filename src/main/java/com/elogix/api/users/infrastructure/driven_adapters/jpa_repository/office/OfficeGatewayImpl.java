package com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.office;

import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.elogix.api.users.domain.model.Office;
import com.elogix.api.users.domain.model.gateways.OfficeGateway;
import com.elogix.api.users.infrastructure.helpers.mappers.OfficeMapper;

public class OfficeGatewayImpl extends
        GenericNamedGatewayImpl<Office, OfficeData, OfficeDataJpaRepository, OfficeMapper> implements OfficeGateway {

    public OfficeGatewayImpl(GenericConfig<Office, OfficeData, OfficeDataJpaRepository, OfficeMapper> config) {
        super(config);
    }
}
