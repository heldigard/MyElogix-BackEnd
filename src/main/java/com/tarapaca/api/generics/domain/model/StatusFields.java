package com.tarapaca.api.generics.domain.model;

import com.tarapaca.api.delivery_orders.domain.model.EStatus;

public record StatusFields(
    boolean pending, boolean production, boolean finished,
    boolean delivered, boolean cancelled, boolean paused) {

  public static StatusFields forStatus(EStatus status) {
    return switch (status) {
      case PENDING -> new StatusFields(true, false, false, false, false, false);
      case PRODUCTION -> new StatusFields(false, true, false, false, false, false);
      case FINISHED -> new StatusFields(false, false, true, false, false, false);
      case DELIVERED -> new StatusFields(false, false, false, true, false, false);
      case CANCELLED -> new StatusFields(false, false, false, false, true, false);
      case PAUSED -> new StatusFields(false, false, false, false, false, true);
      default -> throw new IllegalArgumentException("Invalid Status: " + status);
    };
  }
}
