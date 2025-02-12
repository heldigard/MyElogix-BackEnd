package com.elogix.api.delivery_order.dto;

import com.elogix.api.delivery_order.domain.model.DeliveryOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeliveryOrderResponse {
  private final DeliveryOrder order;

  // Transient statistics - not persisted
  private final int pendingCount;
  private final int productionCount;
  private final int finishedCount;
  private final int deliveredCount;
  private final int cancelledCount;
  private final int pausedCount;

  public DeliveryOrderResponse(
      DeliveryOrder order,
      int pendingCount,
      int productionCount,
      int finishedCount,
      int deliveredCount,
      int cancelledCount,
      int pausedCount) {
    this.order = order;
    this.pendingCount = pendingCount;
    this.productionCount = productionCount;
    this.finishedCount = finishedCount;
    this.deliveredCount = deliveredCount;
    this.cancelledCount = cancelledCount;
    this.pausedCount = pausedCount;
  }
}
