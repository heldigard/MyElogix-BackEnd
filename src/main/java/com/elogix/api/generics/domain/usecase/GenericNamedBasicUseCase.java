package com.elogix.api.generics.domain.usecase;

import com.elogix.api.generics.domain.gateway.GenericNamedBasicGateway;
import com.elogix.api.generics.domain.model.GenericNamedBasic;

/**
 * Abstract base class for use cases handling basic named entities.
 * Extends functionality from GenericBasicUseCase with name-based operations.
 *
 * @param <T> The domain model type that extends GenericNamedBasic, representing
 *            basic entities with name attributes
 * @param <G> The gateway type implementing both GenericNamedBasicGateway<T> and
 *            GenericBasicGateway<T>, providing combined data access operations
 */
public abstract class GenericNamedBasicUseCase<T extends GenericNamedBasic, G extends GenericNamedBasicGateway<T>>
        extends GenericBasicUseCase<T, G> {

    protected GenericNamedBasicUseCase(G gateway) {
        super(gateway);
    }

    public T findByName(String name, boolean includeDeleted) {
        return gateway.findByName(name, includeDeleted);
    }

    public boolean existsByName(String name, boolean includeDeleted) {
        return gateway.existsByName(name, includeDeleted);
    }
}
