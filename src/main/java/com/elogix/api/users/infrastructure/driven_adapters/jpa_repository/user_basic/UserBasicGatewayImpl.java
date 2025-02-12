package com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user_basic;

import java.util.Optional;

import org.hibernate.Session;

import com.elogix.api.generics.infrastructure.repository.GenericBasic.GenericBasicGatewayImpl;
import com.elogix.api.users.domain.model.UserBasic;
import com.elogix.api.users.domain.model.gateways.UserBasicGateway;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;

import jakarta.persistence.EntityManager;

public class UserBasicGatewayImpl
        extends GenericBasicGatewayImpl<UserBasic, UserBasicData, UserBasicDataJpaRepository, UserBasicMapper>
        implements UserBasicGateway {

    public UserBasicGatewayImpl(UserBasicMapper mapper, UserBasicDataJpaRepository repository,
            EntityManager entityManager, String deletedFilter) {
        super(repository, mapper, entityManager, deletedFilter);
    }

    @Override
    public UserBasic findByUsername(String username, boolean isDeleted) {
        Session session = setDeleteFilter(isDeleted);
        Optional<UserBasicData> userBasicData = repository.findByUsername(username);
        session.disableFilter(this.deletedFilter);

        return mapper.toDomain(userBasicData.orElse(null));
    }
}
