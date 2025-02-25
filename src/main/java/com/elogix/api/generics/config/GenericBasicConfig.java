package com.elogix.api.generics.config;

import com.elogix.api.generics.domain.model.GenericEntity;
import com.elogix.api.generics.infrastructure.helpers.GenericMapperGateway;
import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericEntityData;
import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericEntityRepository;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class GenericBasicConfig<T extends GenericEntity, D extends GenericEntityData, R extends GenericEntityRepository<D>, M extends GenericMapperGateway<T, D>> {
  private final R repository;
  private final M mapper;
  private final EntityManager entityManager;
  private final String deletedFilter;

  public GenericBasicConfig(R repository, M mapper, EntityManager entityManager, String deletedFilter) {
    this.repository = repository;
    this.mapper = mapper;
    this.entityManager = entityManager;
    this.deletedFilter = deletedFilter;
  }
}
