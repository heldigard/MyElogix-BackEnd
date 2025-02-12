package com.elogix.api.users.domain.model.gateways;

import com.elogix.api.generics.domain.gateway.GenericGateway;
import com.elogix.api.users.domain.model.ERole;
import com.elogix.api.users.domain.model.RoleModel;

public interface RoleGateway extends GenericGateway<RoleModel> {
    void deleteByName(ERole name);

    RoleModel findByName(ERole name, boolean isDeleted);
}
