package com.elogix.api.generics.domain.usecase;

import com.elogix.api.generics.domain.gateway.GenericGateway;
import com.elogix.api.generics.domain.gateway.GenericNamedGateway;
import com.elogix.api.generics.domain.model.GenericNamed;

/**
 * Abstract base class for use cases handling named entities with generic
 * operations.
 * Provides basic CRUD operations plus name-based operations for domain
 * entities.
 *
 * @param <T> The domain model type that extends GenericNamed, representing
 *            entities
 *            with name-based identification capabilities
 * @param <G> The gateway type that implements both GenericNamedGateway<T> and
 *            GenericGateway<T>, providing combined data access operations
 */
public abstract class GenericNamedUseCase<T extends GenericNamed, G extends GenericNamedGateway<T> & GenericGateway<T>>
        extends GenericUseCase<T, G> {
    protected GenericNamedUseCase(G gateway) {
        super(gateway);
    }

    public T findByName(String name, boolean isDeleted) {
        return gateway.findByName(name, isDeleted);
    }

    public T deleteByName(String name) {
        return gateway.deleteByName(name);
    }
}
