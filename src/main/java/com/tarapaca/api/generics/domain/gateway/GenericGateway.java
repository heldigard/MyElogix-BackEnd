package com.tarapaca.api.generics.domain.gateway;

import java.util.List;

import com.tarapaca.api.generics.domain.model.GenericEntity;

/**
 * Gateway interface that extends basic operations and adds CRUD and auditing
 * capabilities
 *
 * @param <T> Type that extends GenericEntity
 */
public interface GenericGateway<T extends GenericEntity> extends GenericBasicGateway<T> {
    // CRUD Operations
    T save(T entity);

    List<T> saveAll(List<T> entityList);

    T add(T entity);

    T update(T entity);

    void preserveExistingData(T newEntity, T existing);

    T delete(T entity);

    T deleteById(Long id);

    void hardDelete(Long id);

    List<T> deleteAll(List<T> entityList);
}
