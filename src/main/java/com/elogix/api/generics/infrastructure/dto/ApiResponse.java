package com.elogix.api.generics.infrastructure.dto;

import java.util.List;

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
public class ApiResponse<T> {
  private boolean success;
  private String message;
  private DataWrapper<T> data;

  @Getter
  @Setter
  public static class DataWrapper<T> {
    private T item;
    private List<T> items;

    public DataWrapper(T item) {
      this.item = item;
    }

    public DataWrapper(List<T> items) {
      this.items = items;
    }
  }

  public ApiResponse(boolean success, String message) {
    this.success = success;
    this.message = message;
    this.data = null;
  }

  public ApiResponse(String message) {
    this.success = false;
    this.message = message;
    this.data = null;
  }

  public ApiResponse(String message, T item) {
    this.success = true;
    this.message = message;
    this.data = new DataWrapper<>(item);
  }

  public ApiResponse(String message, List<T> items) {
    this.success = true;
    this.message = message;
    this.data = new DataWrapper<>(items);
  }
}
