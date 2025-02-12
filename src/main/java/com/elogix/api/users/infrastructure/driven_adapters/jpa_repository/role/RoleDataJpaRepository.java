package com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.role;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericEntityRepository;
import com.elogix.api.users.domain.model.ERole;

@Repository
public interface RoleDataJpaRepository extends JpaRepository<RoleData, Long>, GenericEntityRepository<RoleData> {
    void deleteByName(ERole name);

    @Query("SELECT r FROM RoleData r")
    Page<RoleData> findAllPagination(Pageable pageable);

    Optional<RoleData> findByName(ERole name);

    @Transactional
    @Modifying
    @Query("UPDATE RoleData r SET r.deletedBy.id = :userId WHERE r.id = :id")
    boolean updateDeletedBy(Long id, Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE RoleData r SET r.deletedAt = :date WHERE r.id = :id")
    boolean updateDeletedAt(Long id, Instant date);
}
