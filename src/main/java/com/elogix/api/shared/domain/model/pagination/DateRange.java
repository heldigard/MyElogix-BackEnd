package com.elogix.api.shared.domain.model.pagination;

import java.time.Instant;

public record DateRange(Instant startDate, Instant endDate) {
}
