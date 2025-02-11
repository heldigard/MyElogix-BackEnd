package com.tarapaca.api.generics.infrastructure.exception;

import com.tarapaca.api.generics.infrastructure.dto.StatusTransitionResponse;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusChangeResponseDTO {
  private boolean success;
  private String message;
  private Long remainingSeconds;
  private StatusTransitionResponse statusResponse;

  public StatusChangeResponseDTO(boolean success, String message, Long remainingSeconds, StatusTransitionResponse statusResponse) {
    this.success = success;
    this.message = message;
    this.remainingSeconds = remainingSeconds;
    this.statusResponse = statusResponse;

  }

  public StatusChangeResponseDTO(boolean success, String message, Long remainingSeconds, Object statusResponse) {
    this.success = success;
    this.message = message;
    this.remainingSeconds = remainingSeconds;
    this.statusResponse = (StatusTransitionResponse) statusResponse;

  }
}
