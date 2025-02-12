package com.elogix.api.users.domain.usecase;

import com.elogix.api.generics.domain.usecase.GenericUseCase;
import com.elogix.api.users.domain.model.ERole;
import com.elogix.api.users.domain.model.RoleModel;
import com.elogix.api.users.domain.model.gateways.RoleGateway;

public class RoleUseCase extends GenericUseCase<RoleModel, RoleGateway> {
    public RoleUseCase(RoleGateway gateway) {
        super(gateway);
    }

    public void deleteByName(ERole name) {
        gateway.deleteByName(name);
    }

    public RoleModel findByName(ERole name, boolean isDeleted) {
        return gateway.findByName(name, isDeleted);
    }
}
