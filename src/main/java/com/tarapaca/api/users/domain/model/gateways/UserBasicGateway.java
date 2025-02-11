package com.tarapaca.api.users.domain.model.gateways;

import com.tarapaca.api.generics.domain.gateway.GenericBasicGateway;
import com.tarapaca.api.users.domain.model.UserBasic;

public interface UserBasicGateway extends GenericBasicGateway<UserBasic> {
    UserBasic findByUsername(String username, boolean isDeleted);
}
