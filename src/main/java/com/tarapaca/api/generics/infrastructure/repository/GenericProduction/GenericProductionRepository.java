package com.tarapaca.api.generics.infrastructure.repository.GenericProduction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import com.tarapaca.api.generics.infrastructure.repository.GenericStatus.GenericStatusRepository;

@NoRepositoryBean
public interface GenericProductionRepository<D extends GenericProductionData>
        extends GenericStatusRepository<D> {

    @Query("SELECT d.isPending FROM #{#entityName} d WHERE d.id = :id")
    boolean getIsPending(Long id);

    @Query("SELECT d.isProduction FROM #{#entityName} d WHERE d.id = :id")
    boolean getIsProduction(Long id);

    @Query("SELECT d.isFinished FROM #{#entityName} d WHERE d.id = :id")
    boolean getIsFinished(Long id);

    @Query("SELECT d.isDelivered FROM #{#entityName} d WHERE d.id = :id")
    boolean getIsDelivered(Long id);

    @Query("SELECT d.isCancelled FROM #{#entityName} d WHERE d.id = :id")
    boolean getIsCancelled(Long id);

    @Query("SELECT d.isPaused FROM #{#entityName} d WHERE d.id = :id")
    boolean getIsPaused(Long id);
}
