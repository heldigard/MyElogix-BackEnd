package com.tarapaca.api.authentication.dto;

import com.tarapaca.api.authentication.domain.model.ETokenType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    @Builder.Default
    private ETokenType tokenType = ETokenType.BEARER;
}
