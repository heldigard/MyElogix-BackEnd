package com.elogix.api.shared.infraestructure.notification;

import org.springframework.stereotype.Service;

import com.elogix.api.delivery_order.domain.model.DeliveryOrder;
import com.elogix.api.delivery_order.domain.model.DeliveryOrderBasic;
import com.elogix.api.delivery_order.infrastructure.websocket.DeliveryOrderBasicWebSocketHandler;
import com.elogix.api.delivery_order.infrastructure.websocket.DeliveryOrderWebSocketHandler;
import com.elogix.api.product_order.domain.model.ProductOrder;
import com.elogix.api.product_order.infrastructure.websocket.ProductOrderWebSocketHandler;

@Service
public class NotificationService {
  private final DeliveryOrderWebSocketHandler deliveryOrderHandler;
  private final ProductOrderWebSocketHandler productOrderHandler;
  private final DeliveryOrderBasicWebSocketHandler deliveryOrderBasicHandler;

  public NotificationService(DeliveryOrderWebSocketHandler deliveryOrderHandler,
                           ProductOrderWebSocketHandler productOrderHandler,
                           DeliveryOrderBasicWebSocketHandler deliveryOrderBasicHandler) {
    this.deliveryOrderHandler = deliveryOrderHandler;
    this.productOrderHandler = productOrderHandler;
    this.deliveryOrderBasicHandler = deliveryOrderBasicHandler;
  }

  public void notifyDeliveryOrderChange(Long orderId, DeliveryOrder order, String status) {
    deliveryOrderHandler.notifyOrderStatusChange(orderId, order, status);
  }

  public void notifyProductOrderChange(Long orderId, ProductOrder order, String status) {
    productOrderHandler.notifyOrderStatusChange(orderId, order, status);
  }

  public void notifyDeliveryOrderBasicChange(Long orderId, DeliveryOrderBasic order, String status) {
    deliveryOrderBasicHandler.notifyOrderStatusChange(orderId, order, status);
  }
}
