package com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.price_list;

import com.elogix.api.delivery_orders.domain.model.PriceList;
import com.elogix.api.delivery_orders.domain.model.gateways.PriceListGateway;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.elogix.api.product.infrastructure.helper.mapper.PriceListMapper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

public class PriceListGatewayImpl
        extends GenericNamedGatewayImpl<PriceList, PriceListData, PriceListDataJpaRepository, PriceListMapper>
        implements PriceListGateway {

    public PriceListGatewayImpl(
            PriceListDataJpaRepository repository,
            PriceListMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
    }

    @Override
    public PriceList updateIsActive(Long id, boolean isActive) {
        return mapper.toDomain(repository.updateIsActive(id, isActive));
    }
}
