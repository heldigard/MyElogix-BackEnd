package com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.user;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tarapaca.api.generics.infrastructure.repository.GenericEntity.GenericEntityRepository;
import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.role.RoleData;

@Repository
public interface UserDataJpaRepository extends JpaRepository<UserData, Long>, GenericEntityRepository<UserData> {
    @Transactional
    @Modifying
    @Query("UPDATE UserData u SET u.roles = :roles WHERE u.id = :id")
    int updateRole(@Param("id") Long id, @Param("roles") Collection<RoleData> roles);

    void deleteByEmail(String email);

    void deleteByUsername(String username);

    Optional<UserData> findByEmail(String email);

    Optional<UserData> findByUsername(String username);

    boolean existsByUsername(String username);
}
