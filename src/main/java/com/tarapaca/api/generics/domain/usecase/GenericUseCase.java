package com.tarapaca.api.generics.domain.usecase;

import java.util.List;

import com.tarapaca.api.generics.domain.gateway.GenericGateway;
import com.tarapaca.api.generics.domain.model.GenericEntity;

public abstract class GenericUseCase<T extends GenericEntity, G extends GenericGateway<T>>
        extends GenericBasicUseCase<T, G> {

    protected GenericUseCase(G gateway) {
        super(gateway);
    }

    public T save(T entity) {
        return gateway.save(entity);
    }

    public List<T> saveAll(List<T> entityList) {
        return gateway.saveAll(entityList);
    }

    public T add(T entity) {
        return gateway.add(entity);
    }

    public T update(T entity) {
        return gateway.update(entity);
    }

    public void preserveExistingData(T newEntity, T existing) {
        gateway.preserveExistingData(newEntity, existing);
    }

    public T delete(T entity) {
        return gateway.delete(entity);
    }

    public T deleteById(Long id) {
        return gateway.deleteById(id);
    }

    public List<T> deleteAll(List<T> entityList) {
        return gateway.deleteAll(entityList);
    }
}
