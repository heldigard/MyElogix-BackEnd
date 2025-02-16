package com.elogix.api.generics.infrastructure.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelResponse<T> {

  @Builder.Default
  private List<T> entities = new ArrayList<>();

  @Builder.Default
  private Set<String> errors = new HashSet<>();

  public void addError(String message, Object... args) {
    this.errors.add(String.format(message, args));
  }
}
