package com.tarapaca.api.authentication.domain.model;

public record UserDetailsWithToken(
    UserDetailsImpl userDetails,
    TokenModel tokenModel) {
}
