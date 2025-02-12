package com.elogix.api.authentication.infrastructure.repository;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.elogix.api.authentication.config.JwtProperties;
import com.elogix.api.authentication.domain.usecase.TokenUseCase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {
    private final TokenUseCase tokenUseCase;
    private final JwtProperties jwtProperties;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {
        final String authorizationHeader = request.getHeader(jwtProperties.getHeader());
        if (authorizationHeader == null || !authorizationHeader.startsWith(jwtProperties.getPrefix() + " ")) {
            return;
        }

        final String token = authorizationHeader.substring(7);
        try {
            tokenUseCase.revokeToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
