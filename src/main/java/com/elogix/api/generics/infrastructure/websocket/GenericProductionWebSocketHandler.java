package com.elogix.api.generics.infrastructure.websocket;

import java.util.Optional;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.elogix.api.delivery_order.dto.DeliveryOrderResponse;
import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.generics.domain.model.GenericProduction;

/**
 * Manejador genérico para WebSockets relacionados con producción.
 * Proporciona funcionalidad para enviar notificaciones sobre cambios de estado.
 *
 * @param <T> Tipo de objeto de orden/producción a manejar
 */
@Component
public abstract class GenericProductionWebSocketHandler<T> {

  protected final SimpMessagingTemplate messagingTemplate;
  protected final String topicPrefix;
  private static final String TOPIC_FORMAT = "/topic/%s";
  private static final String TOPIC_ID_FORMAT = "/topic/%s/%s";

  protected GenericProductionWebSocketHandler(SimpMessagingTemplate messagingTemplate, String topicPrefix) {
    this.messagingTemplate = messagingTemplate;
    this.topicPrefix = topicPrefix;
  }

  /**
   * Notifica un cambio de estado usando un enum de estado
   */
  public void notifyOrderStatusChange(EStatus statusEnum, T order, String statusText) {
    String destination = buildDestination(statusEnum);
    sendNotification(destination, order, statusText);
  }

  /**
   * Notifica un cambio de estado para una orden específica por ID
   */
  public void notifyOrderStatusChange(Long orderId, T order, String status) {
    notifyOrderStatusChangeInternal(Optional.ofNullable(orderId), order, status);
  }

  /**
   * Notifica un cambio de estado sin especificar ID
   */
  public void notifyOrderStatusChange(T order, String status) {
    notifyOrderStatusChangeInternal(Optional.empty(), order, status);
  }

  // Método interno unificado
  private void notifyOrderStatusChangeInternal(Optional<Long> orderId, T order, String status) {
    String destination = buildDestination(orderId);
    sendNotification(destination, order, status);
  }

  /**
   * Construye el destination basado en ID opcional
   */
  private String buildDestination(Optional<Long> orderId) {
    return orderId
        .map(id -> String.format(TOPIC_ID_FORMAT, topicPrefix, id.toString()))
        .orElseGet(() -> String.format(TOPIC_FORMAT, topicPrefix));
  }

  /**
   * Construye el destination basado en enum de estado
   */
  private String buildDestination(EStatus statusEnum) {
    return String.format(TOPIC_ID_FORMAT, topicPrefix, statusEnum.toString());
  }

  /**
   * Envía una notificación al destination especificado
   */
  protected void sendNotification(String destination, T order, String status) {
    GenericProductionWebSocketMessage<T> socketMessage = createSocketMessage(order, status);
    messagingTemplate.convertAndSend(destination, socketMessage);
  }

  /**
   * Crea un mensaje para WebSocket con los detalles proporcionados
   */
  protected GenericProductionWebSocketMessage<T> createSocketMessage(T data, String status) {
    String className = data.getClass().getSimpleName();
    GenericProduction order = null;

    if (data instanceof GenericProduction genericProduction) {
      order = genericProduction;
    } else if (data instanceof DeliveryOrderResponse deliveryOrderResponse) {
      order = deliveryOrderResponse.getOrder();
    }

    Long orderId = order != null ? order.getId() : null;
    GenericProductionWebSocketMessage.GenericProductionWebSocketMessageBuilder<T> builder = GenericProductionWebSocketMessage
        .<T>builder()
        .id(orderId)
        .data(data)
        .status(status)
        .message(String.format("%s %s", status, className));

    return builder.build();
  }
}
