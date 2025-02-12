package com.elogix.api.authentication.infrastructure.exception;

import java.io.Serial;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtTokenException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String token;
    private final String errorMessage;

    public JwtTokenException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
        this.token = token;
        this.errorMessage = message;
    }

    public JwtTokenException(String message, Throwable cause) {
        super(message, cause);
        this.token = null;
        this.errorMessage = message;
    }

    public String getToken() {
        return token;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
