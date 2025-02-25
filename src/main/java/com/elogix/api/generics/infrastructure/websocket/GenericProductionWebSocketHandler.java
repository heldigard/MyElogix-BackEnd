package com.elogix.api.generics.infrastructure.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.domain.model.GenericProduction;

@Component
public abstract class GenericProductionWebSocketHandler<T extends GenericProduction> {

  private final SimpMessagingTemplate messagingTemplate;
  private final String topicPrefix;

  protected GenericProductionWebSocketHandler(SimpMessagingTemplate messagingTemplate, String topicPrefix) {
    this.messagingTemplate = messagingTemplate;
    this.topicPrefix = topicPrefix;
  }

  public void notifyOrderStatusChange(Long orderId, T order, String status) {
    GenericProductionWebSocketMessage<T> message = new GenericProductionWebSocketMessage<>(
        orderId,
        order,
        status,
        "Order status updated");

    messagingTemplate.convertAndSend(
        String.format("/topic/%s/%d", topicPrefix, orderId),
        message);
  }
}
