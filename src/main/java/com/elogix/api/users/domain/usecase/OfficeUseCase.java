package com.elogix.api.users.domain.usecase;

import com.elogix.api.generics.domain.usecase.GenericNamedUseCase;
import com.elogix.api.users.domain.model.Office;
import com.elogix.api.users.domain.model.gateways.OfficeGateway;

public class OfficeUseCase extends GenericNamedUseCase<Office, OfficeGateway> {
    public OfficeUseCase(OfficeGateway gateway) {
        super(gateway);
    }
}
