package com.elogix.api.delivery_order.infrastructure.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.elogix.api.delivery_order.domain.model.DeliveryOrder;
import com.elogix.api.generics.infrastructure.websocket.GenericProductionWebSocketHandler;

@Component
public class DeliveryOrderWebSocketHandler extends GenericProductionWebSocketHandler<DeliveryOrder> {

  public DeliveryOrderWebSocketHandler(SimpMessagingTemplate messagingTemplate) {
    super(messagingTemplate, "delivery-orders");
  }
}
