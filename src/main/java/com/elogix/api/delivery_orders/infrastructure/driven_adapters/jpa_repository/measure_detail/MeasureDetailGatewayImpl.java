package com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.measure_detail;

import com.elogix.api.delivery_orders.domain.model.MeasureDetail;
import com.elogix.api.delivery_orders.domain.model.gateways.MeasureDetailGateway;
import com.elogix.api.delivery_orders.infrastructure.helpers.mappers.MeasureDetailMapper;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

public class MeasureDetailGatewayImpl
        extends
        GenericNamedGatewayImpl<MeasureDetail, MeasureDetailData, MeasureDetailDataJpaRepository, MeasureDetailMapper>
        implements MeasureDetailGateway {

    public MeasureDetailGatewayImpl(
            MeasureDetailDataJpaRepository repository,
            MeasureDetailMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
    }
}
