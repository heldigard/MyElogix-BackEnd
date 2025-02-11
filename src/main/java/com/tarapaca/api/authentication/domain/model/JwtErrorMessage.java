package com.tarapaca.api.authentication.domain.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtErrorMessage {
    private int statusCode;
    private Instant timestamp;
    private String message;
    private String description;
}
