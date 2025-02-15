package com.tarapaca.api.authentication.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JwtAuthenticateException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public JwtAuthenticateException(String username, String message) {
        super(String.format("Failed for [%s]: %s", username, message));
    }
}
