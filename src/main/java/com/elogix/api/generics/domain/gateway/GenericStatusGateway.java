package com.elogix.api.generics.domain.gateway;

import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.generics.domain.model.GenericEntity;

public interface GenericStatusGateway<T extends GenericEntity> extends GenericGateway<T> {

  T updateStatus(Long id, Status status);

  T updateStatus(Long id, EStatus status);

  T updateStatus(Long id, String status);

  Status getStatus(Long id);
}
