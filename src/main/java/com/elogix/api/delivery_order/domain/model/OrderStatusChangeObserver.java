package com.elogix.api.delivery_order.domain.model;

import com.elogix.api.delivery_orders.domain.model.EStatus;

public interface OrderStatusChangeObserver {
  void onStatusChanged(Long orderId, EStatus newStatus);
}
