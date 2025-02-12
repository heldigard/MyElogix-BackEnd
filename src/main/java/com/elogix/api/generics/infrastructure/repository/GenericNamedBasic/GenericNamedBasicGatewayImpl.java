package com.elogix.api.generics.infrastructure.repository.GenericNamedBasic;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import com.elogix.api.generics.domain.gateway.GenericNamedBasicGateway;
import com.elogix.api.generics.domain.model.GenericNamedBasic;
import com.elogix.api.generics.infrastructure.helpers.GenericNamedBasicMapper;
import com.elogix.api.generics.infrastructure.repository.GenericBasic.GenericBasicGatewayImpl;

import jakarta.persistence.EntityManager;

public abstract class GenericNamedBasicGatewayImpl<T extends GenericNamedBasic, D extends GenericNamedBasicData, R extends GenericNamedBasicRepository<D>, M extends GenericNamedBasicMapper<T, D>>
        extends GenericBasicGatewayImpl<T, D, R, M>
        implements GenericNamedBasicGateway<T> {

    protected GenericNamedBasicGatewayImpl(
            R repository,
            M mapper,
            EntityManager entityManager,
            String deletedFilter) {
        super(repository, mapper, entityManager, deletedFilter);
        this.deletedFilter = deletedFilter;
    }

    @Override
    public void preserveExistingData(T newEntity, T existing) {
        super.preserveExistingData(newEntity, existing);

        if (newEntity.getName() == null)
            newEntity.setName(existing.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public T findByName(String name, boolean isDeleted) {
        Optional<D> entityData;
        try (Session session = setDeleteFilter(isDeleted)) {
            entityData = repository.findByName(name);
            session.disableFilter(this.deletedFilter);
        }
        return mapper.toDomain(
                entityData.orElseThrow(() -> new NoSuchElementException("Entity not found with name: " + name)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findByNameContaining(String name, boolean isDeleted) {
        List<D> entityDataList;
        try (Session session = setDeleteFilter(isDeleted)) {
            entityDataList = repository.findByNameContaining(name);
            session.disableFilter(this.deletedFilter);
        }
        return entityDataList.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findByNameLike(String pattern, boolean isDeleted) {
        List<D> entityDataList;
        try (Session session = setDeleteFilter(isDeleted)) {
            entityDataList = repository.findByNameLike(pattern);
            session.disableFilter(this.deletedFilter);
        }
        return entityDataList.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name, boolean isDeleted) {
        boolean exists;
        try (Session session = setDeleteFilter(isDeleted)) {
            exists = repository.existsByName(name);
            session.disableFilter(this.deletedFilter);
        }
        return exists;
    }
}
