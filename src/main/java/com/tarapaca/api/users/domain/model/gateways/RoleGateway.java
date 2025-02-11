package com.tarapaca.api.users.domain.model.gateways;

import com.tarapaca.api.generics.domain.gateway.GenericGateway;
import com.tarapaca.api.users.domain.model.ERole;
import com.tarapaca.api.users.domain.model.RoleModel;

public interface RoleGateway extends GenericGateway<RoleModel> {
    void deleteByName(ERole name);

    RoleModel findByName(ERole name, boolean isDeleted);
}
