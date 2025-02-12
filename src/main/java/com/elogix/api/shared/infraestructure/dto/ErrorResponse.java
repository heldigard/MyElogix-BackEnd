package com.elogix.api.shared.infraestructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  private String message;
  private String details;
  private long timestamp;

  public ErrorResponse(String message, String details) {
    this.message = message;
    this.details = details;
    this.timestamp = System.currentTimeMillis();
  }
}
