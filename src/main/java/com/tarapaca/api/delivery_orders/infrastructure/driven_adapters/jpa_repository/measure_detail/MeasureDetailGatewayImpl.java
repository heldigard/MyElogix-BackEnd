package com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.measure_detail;

import com.tarapaca.api.delivery_orders.domain.model.MeasureDetail;
import com.tarapaca.api.delivery_orders.domain.model.gateways.MeasureDetailGateway;
import com.tarapaca.api.delivery_orders.infrastructure.helpers.mappers.MeasureDetailMapper;
import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.tarapaca.api.shared.infraestructure.helpers.UpdateUtils;

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
