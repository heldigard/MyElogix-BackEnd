package com.elogix.api.authentication.infrastructure.repository;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.elogix.api.authentication.domain.model.JwtErrorMessage;
import com.elogix.api.authentication.infrastructure.exception.JwtAuthenticateException;
import com.elogix.api.authentication.infrastructure.exception.JwtRefreshException;
import com.elogix.api.authentication.infrastructure.exception.JwtTokenException;

@RestControllerAdvice
public class JwtControllerAdvice {
    @ExceptionHandler(value = JwtRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public JwtErrorMessage handleTokenRefreshException(JwtRefreshException ex, WebRequest request) {
        return new JwtErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = JwtAuthenticateException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public JwtErrorMessage handleTokenAuthenticateException(JwtAuthenticateException ex, WebRequest request) {
        return new JwtErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = JwtTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public JwtErrorMessage handleTokenException(JwtTokenException ex, WebRequest request) {
        return new JwtErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false));
    }
}
