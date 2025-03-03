package com.elogix.api.delivery_order.dto;

import java.util.Collections;
import java.util.List;

import com.elogix.api.delivery_order.domain.model.DeliveryOrder;
import com.elogix.api.product_order.domain.model.ProductOrder;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE) // Para Lombok
public class DeliveryOrderResponse {
  private DeliveryOrder order;
  private int pendingCount;
  private int productionCount;
  private int finishedCount;
  private int deliveredCount;
  private int cancelledCount;
  private int pausedCount;

  // Constructor privado para el Builder
  @Builder(access = AccessLevel.PRIVATE)
  private DeliveryOrderResponse(DeliveryOrder order, int pendingCount, int productionCount,
      int finishedCount, int deliveredCount, int cancelledCount, int pausedCount) {
    this.order = order;
    this.pendingCount = pendingCount;
    this.productionCount = productionCount;
    this.finishedCount = finishedCount;
    this.deliveredCount = deliveredCount;
    this.cancelledCount = cancelledCount;
    this.pausedCount = pausedCount;
  }

  // Método builder personalizado que solo necesita la DeliveryOrder
  public static DeliveryOrderResponse of(DeliveryOrder order) {
    return createWithStatistics(order);
  }

  private static DeliveryOrderResponse createWithStatistics(DeliveryOrder order) {
    if (order == null) {
      return DeliveryOrderResponse.builder().build();
    }

    List<ProductOrder> productOrders = order.getProductOrders();
    if (productOrders == null) {
      productOrders = Collections.emptyList();
    }

    // Contadores para cada estado
    int pending = 0;
    int production = 0;
    int finished = 0;
    int delivered = 0;
    int cancelled = 0;
    int paused = 0;

    // Una sola iteración para contar todos los estados
    for (ProductOrder po : productOrders) {
      if (po.isPending())
        pending++;
      else if (po.isProduction())
        production++;
      else if (po.isFinished())
        finished++;
      else if (po.isDelivered())
        delivered++;
      else if (po.isCancelled())
        cancelled++;
      else if (po.isPaused())
        paused++;
    }

    return DeliveryOrderResponse.builder()
        .order(order)
        .pendingCount(pending)
        .productionCount(production)
        .finishedCount(finished)
        .deliveredCount(delivered)
        .cancelledCount(cancelled)
        .pausedCount(paused)
        .build();
  }

  // El setter de order también actualiza los contadores
  public void setOrder(DeliveryOrder order) {
    DeliveryOrderResponse response = createWithStatistics(order);
    this.order = response.getOrder();
    this.pendingCount = response.getPendingCount();
    this.productionCount = response.getProductionCount();
    this.finishedCount = response.getFinishedCount();
    this.deliveredCount = response.getDeliveredCount();
    this.cancelledCount = response.getCancelledCount();
    this.pausedCount = response.getPausedCount();
  }
}
