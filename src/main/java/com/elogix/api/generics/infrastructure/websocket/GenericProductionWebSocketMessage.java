package com.elogix.api.generics.infrastructure.websocket;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericProductionWebSocketMessage<T> {
  private Long id;
  private T data;
  private String status;
  private String message;
  @Builder.Default
  private Instant timestamp = Instant.now();

  public GenericProductionWebSocketMessage(Long id, T data, String status, String message) {
    this.id = id;
    this.data = data;
    this.status = status;
    this.message = message;
  }
}
