package com.elogix.api.shared.infraestructure.notification;

import org.springframework.stereotype.Service;

import com.elogix.api.delivery_order.domain.model.DeliveryOrder;
import com.elogix.api.delivery_order.domain.model.DeliveryOrderBasic;
import com.elogix.api.delivery_order.dto.DeliveryOrderResponse;
import com.elogix.api.delivery_order.infrastructure.helper.mapper.DeliveryOrderMapper;
import com.elogix.api.delivery_order.infrastructure.websocket.DeliveryOrderBasicWebSocketHandler;
import com.elogix.api.delivery_order.infrastructure.websocket.DeliveryOrderWebSocketHandler;
import com.elogix.api.product_order.domain.model.ProductOrder;
import com.elogix.api.product_order.infrastructure.websocket.ProductOrderWebSocketHandler;

@Service
public class NotificationService {
  private final DeliveryOrderWebSocketHandler deliveryOrderHandler;
  private final ProductOrderWebSocketHandler productOrderHandler;
  private final DeliveryOrderBasicWebSocketHandler deliveryOrderBasicHandler;
  private final DeliveryOrderMapper mapper;

  public NotificationService(DeliveryOrderWebSocketHandler deliveryOrderHandler,
      ProductOrderWebSocketHandler productOrderHandler,
      DeliveryOrderBasicWebSocketHandler deliveryOrderBasicHandler,
      DeliveryOrderMapper mapper) {
    this.deliveryOrderHandler = deliveryOrderHandler;
    this.productOrderHandler = productOrderHandler;
    this.deliveryOrderBasicHandler = deliveryOrderBasicHandler;
    this.mapper = mapper;
  }

  public void notifyDeliveryOrderBasicChange(DeliveryOrderBasic order, String status) {
    deliveryOrderBasicHandler.notifyOrderStatusChange(order, status);
    deliveryOrderBasicHandler.notifyOrderStatusChange(order.getStatus().getName(), order, status);
  }

  public void notifyDeliveryOrderBasicChange(DeliveryOrder order, String status) {
    notifyDeliveryOrderBasicChange(mapper.toBasic(order), status);
  }

  public void notifyDeliveryOrderChange(Long orderId, DeliveryOrder order, String status) {
    DeliveryOrderResponse response = DeliveryOrderResponse.of(order);
    deliveryOrderHandler.notifyOrderStatusChange(orderId, response, status);

    notifyDeliveryOrderBasicChange(order, status);
  }

  public void notifyProductOrderChange(Long orderId, ProductOrder order, String status) {
    productOrderHandler.notifyOrderStatusChange(orderId, order, status);
  }
}
