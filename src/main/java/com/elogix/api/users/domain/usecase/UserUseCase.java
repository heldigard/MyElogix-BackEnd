package com.elogix.api.users.domain.usecase;

import java.util.Set;

import com.elogix.api.generics.domain.usecase.GenericUseCase;
import com.elogix.api.users.domain.model.RoleModel;
import com.elogix.api.users.domain.model.UserModel;
import com.elogix.api.users.domain.model.gateways.UserGateway;

public class UserUseCase extends GenericUseCase<UserModel, UserGateway> {
    public UserUseCase(UserGateway gateway) {
        super(gateway);
    }

    public int updateRole(Long id, Set<RoleModel> roleModels) {
        if (id == null || roleModels.isEmpty()) {
            return 0;
        }
        return gateway.updateRole(id, roleModels);
    }

    public void deleteByEmail(String email) {
        gateway.deleteByEmail(email);
    }

    public void deleteByUsername(String username) {
        gateway.deleteByUsername(username);
    }

    public UserModel findByEmail(String email, boolean isDeleted) {
        return gateway.findByEmail(email, isDeleted);
    }

    public UserModel findByUsername(String username, boolean isDeleted) {
        return gateway.findByUsername(username, isDeleted);
    }
}
