package com.elogix.api.generics.domain.gateway;

import com.elogix.api.generics.domain.model.GenericEntity;

/**
 * Generic gateway interface that combines named and basic entity operations
 *
 * @param <T> Entity type that extends GenericEntity
 */
public interface GenericNamedGateway<T extends GenericEntity>
                extends GenericGateway<T>, GenericNamedBasicGateway<T> {
        T deleteByName(String name);
}
