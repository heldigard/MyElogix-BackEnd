package com.elogix.api.shared.infraestructure.entry_points.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private String message;
    private boolean success;
}
