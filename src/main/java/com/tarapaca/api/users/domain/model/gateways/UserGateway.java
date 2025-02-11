package com.tarapaca.api.users.domain.model.gateways;

import java.util.Set;

import com.tarapaca.api.generics.domain.gateway.GenericGateway;
import com.tarapaca.api.users.domain.model.RoleModel;
import com.tarapaca.api.users.domain.model.UserModel;

public interface UserGateway extends GenericGateway<UserModel> {
    int updateRole(Long id, Set<RoleModel> roleModels);

    void deleteByEmail(String email);

    void deleteByUsername(String username);

    UserModel findByEmail(String email, boolean isDeleted);

    UserModel findByUsername(String username, boolean isDeleted);
}
