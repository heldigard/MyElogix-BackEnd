package com.tarapaca.api.generics.infrastructure.repository.GenericBasic;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base repository interface that defines basic database operations
 *
 * @param <D> Entity type that extends GenericBasicEntityData
 */
@NoRepositoryBean
public interface GenericBasicRepository<D extends GenericBasicEntityData> extends JpaRepository<D, Long> {
    List<D> findByIdIn(List<Long> idList, Sort sort);

    Page<D> findAll(Pageable pageable);

    List<D> findAll(Sort sort);

    Optional<D> findById(Long id);

    boolean existsById(Long id);
}
