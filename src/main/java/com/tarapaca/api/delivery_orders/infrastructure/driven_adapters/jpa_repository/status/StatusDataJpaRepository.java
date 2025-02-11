package com.tarapaca.api.delivery_orders.infrastructure.driven_adapters.jpa_repository.status;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tarapaca.api.delivery_orders.domain.model.EStatus;
import com.tarapaca.api.generics.infrastructure.repository.GenericBasic.GenericBasicRepository;

@Repository
public interface StatusDataJpaRepository
        extends GenericBasicRepository<StatusData> {
    Optional<StatusData> findByName(EStatus name);

    List<StatusData> findByNameIn(Collection<EStatus> names);

    void deleteByName(EStatus name);
}
