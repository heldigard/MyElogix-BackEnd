package com.elogix.api.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshRequest {
    @NotBlank
    private String refreshToken;
}
