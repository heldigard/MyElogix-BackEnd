package com.elogix.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.price_list;

import com.elogix.api.delivery_orders.domain.model.PriceList;
import com.elogix.api.delivery_orders.domain.model.gateways.PriceListGateway;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.elogix.api.product.infrastructure.helper.mapper.PriceListMapper;

public class PriceListGatewayImpl
        extends GenericNamedGatewayImpl<PriceList, PriceListData, PriceListDataJpaRepository, PriceListMapper>
        implements PriceListGateway {

    public PriceListGatewayImpl(
            GenericConfig<PriceList, PriceListData, PriceListDataJpaRepository, PriceListMapper> config) {
        super(config);
    }

    @Override
    public PriceList updateIsActive(Long id, boolean isActive) {
        return mapper.toDomain(repository.updateIsActive(id, isActive));
    }
}
