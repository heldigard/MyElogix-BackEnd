package com.tarapaca.api.users.domain.usecase;

import com.tarapaca.api.generics.domain.usecase.GenericBasicUseCase;
import com.tarapaca.api.users.domain.model.UserBasic;
import com.tarapaca.api.users.domain.model.gateways.UserBasicGateway;

public class UserBasicUseCase extends GenericBasicUseCase<UserBasic, UserBasicGateway> {

    public UserBasicUseCase(UserBasicGateway gateway) {
        super(gateway);
    }

    public UserBasic findByUsername(String username, boolean isDeleted) {
        return gateway.findByUsername(username, isDeleted);
    }
}
