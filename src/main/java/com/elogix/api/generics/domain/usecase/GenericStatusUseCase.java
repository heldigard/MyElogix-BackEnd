package com.elogix.api.generics.domain.usecase;

import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.generics.domain.gateway.GenericStatusGateway;
import com.elogix.api.generics.domain.model.GenericEntity;

public abstract class GenericStatusUseCase<T extends GenericEntity, G extends GenericStatusGateway<T>>
    extends GenericUseCase<T, G> {

  protected GenericStatusUseCase(G gateway) {
    super(gateway);
  }

  public T updateStatus(Long id, Status status) {
    return gateway.updateStatus(id, status);
  }

  public T updateStatus(Long id, EStatus status) {
    return gateway.updateStatus(id, status);
  }

  public T updateStatus(Long id, String status) {
    return gateway.updateStatus(id, status);
  }

  Status getStatus(Long id) {
    return gateway.getStatus(id);
  }
}
