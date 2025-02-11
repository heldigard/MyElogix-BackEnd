package com.tarapaca.api.shared.domain.model.pagination;

import java.time.Instant;

import lombok.Value;

@Value
public class DateRange {
  Instant startDate;
  Instant endDate;
}
