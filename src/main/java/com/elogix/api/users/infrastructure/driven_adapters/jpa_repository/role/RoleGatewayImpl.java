package com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.role;

import java.util.Optional;

import org.hibernate.Session;

import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericGatewayImpl;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.users.domain.model.ERole;
import com.elogix.api.users.domain.model.RoleModel;
import com.elogix.api.users.domain.model.gateways.RoleGateway;
import com.elogix.api.users.infrastructure.helpers.mappers.RoleMapper;

import jakarta.persistence.EntityManager;

public class RoleGatewayImpl extends GenericGatewayImpl<RoleModel, RoleData, RoleDataJpaRepository, RoleMapper>
        implements RoleGateway {

    public RoleGatewayImpl(
            RoleDataJpaRepository repository,
            RoleMapper mapper,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
    }

    @Override
    public void deleteByName(ERole name) {
        repository.deleteByName(name);
    }

    public RoleModel findByName(ERole name, boolean isDeleted) {
        Session session = setDeleteFilter(isDeleted);
        Optional<RoleData> roleData = repository.findByName(name);
        session.disableFilter(this.deletedFilter);

        return mapper.toDomain(roleData.orElse(new RoleData()));
    }
}
