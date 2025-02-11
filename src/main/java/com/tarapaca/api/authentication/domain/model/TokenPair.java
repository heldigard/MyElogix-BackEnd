package com.tarapaca.api.authentication.domain.model;

public record TokenPair(String accessToken, String refreshToken) {
}
