package com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user_basic;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.elogix.api.generics.infrastructure.repository.GenericBasic.GenericBasicRepository;

@Repository
public interface UserBasicDataJpaRepository
        extends JpaRepository<UserBasicData, Long>, GenericBasicRepository<UserBasicData> {
    Optional<UserBasicData> findByUsername(String username);

    @Query("SELECT u FROM UserBasicData u")
    Page<UserBasicData> findAllPagination(Pageable pageable);
}
