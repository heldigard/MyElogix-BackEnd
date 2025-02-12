package com.elogix.api.users.domain.model.gateways;

import com.elogix.api.generics.domain.gateway.GenericBasicGateway;
import com.elogix.api.users.domain.model.UserBasic;

public interface UserBasicGateway extends GenericBasicGateway<UserBasic> {
    UserBasic findByUsername(String username, boolean isDeleted);
}
