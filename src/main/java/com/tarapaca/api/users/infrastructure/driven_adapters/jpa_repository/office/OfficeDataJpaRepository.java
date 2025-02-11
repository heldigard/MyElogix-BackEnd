package com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.office;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tarapaca.api.generics.infrastructure.repository.GenericNamed.GenericNamedRepository;

@Repository
public interface OfficeDataJpaRepository
        extends JpaRepository<OfficeData, Long>, GenericNamedRepository<OfficeData> {
    int deleteByName(String name);

    @Query("SELECT o FROM OfficeData o")
    Page<OfficeData> findAllPagination(Pageable pageable);

    Optional<OfficeData> findByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE OfficeData o SET o.deletedAt = :date WHERE o.id = :id")
    boolean updateDeletedAt(Long id, Instant date);
}
