package com.tarapaca.api.shared.domain.model.pagination;

import java.time.Instant;

import lombok.Value;

public record DateRange(Instant startDate, Instant endDate) {
}
