package com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.price_list;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedRepository;

@Repository
public interface PriceListDataJpaRepository
                extends GenericNamedRepository<PriceListData> {

        @Query("UPDATE PriceListData p SET p.isActive = :isActive WHERE p.id = :id")
        PriceListData updateIsActive(Long id, boolean isActive);
}
