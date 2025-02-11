package com.tarapaca.api.shared.application.config;

import java.time.Instant;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class InstantConverter implements AttributeConverter<Instant, Instant> {

  @Override
  public Instant convertToDatabaseColumn(Instant attribute) {
    return attribute;
  }

  @Override
  public Instant convertToEntityAttribute(Instant dbData) {
    return dbData;
  }
}
