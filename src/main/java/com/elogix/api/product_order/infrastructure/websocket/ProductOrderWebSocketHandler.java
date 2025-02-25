package com.elogix.api.product_order.infrastructure.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.infrastructure.websocket.GenericProductionWebSocketHandler;
import com.elogix.api.product_order.domain.model.ProductOrder;

@Component
public class ProductOrderWebSocketHandler extends GenericProductionWebSocketHandler<ProductOrder> {

  public ProductOrderWebSocketHandler(SimpMessagingTemplate messagingTemplate) {
    super(messagingTemplate, "product-orders");
  }
}
