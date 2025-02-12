package com.elogix.api.users.domain.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private Long id;
    private String name;

    private Instant deletedAt;

    @Builder.Default
    @JsonProperty(value = "isDeleted", defaultValue = "false")
    private boolean isDeleted = false;
}
