package com.elogix.api.generics.infrastructure.websocket;

import com.elogix.api.generics.domain.model.GenericProduction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericProductionWebSocketMessage<T extends GenericProduction> {
  private Long id;
  private T order;
  private String status;
  private String message;
}
