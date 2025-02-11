package com.tarapaca.api.delivery_order.domain.model;

import com.tarapaca.api.delivery_orders.domain.model.EStatus;

public interface OrderStatusChangeObserver {
  void onStatusChanged(Long orderId, EStatus newStatus);
}
