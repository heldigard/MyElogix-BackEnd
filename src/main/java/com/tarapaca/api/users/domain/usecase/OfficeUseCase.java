package com.tarapaca.api.users.domain.usecase;

import com.tarapaca.api.generics.domain.usecase.GenericNamedUseCase;
import com.tarapaca.api.users.domain.model.Office;
import com.tarapaca.api.users.domain.model.gateways.OfficeGateway;

public class OfficeUseCase extends GenericNamedUseCase<Office, OfficeGateway> {
    public OfficeUseCase(OfficeGateway gateway) {
        super(gateway);
    }
}
