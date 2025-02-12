package com.elogix.api.shared.infraestructure.helpers;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import com.elogix.api.shared.domain.model.pagination.DateRange;

public class DateUtils {
    private DateUtils() {
        // Hide implicit constructor
    }

    public static Instant parseDateTime(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        return formatter.parse(dateTimeStr, Instant::from);
    }

    public static DateRange parseDateRange(String startDate, String endDate) {
        Instant start = parseDateTime(startDate);
        Instant end = parseDateTime(endDate);
        return new DateRange(start, end);
    }
}
