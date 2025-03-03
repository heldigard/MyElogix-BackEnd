package com.elogix.api.authentication.infrastructure.filters;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.elogix.api.authentication.config.JwtProperties;
import com.elogix.api.authentication.domain.model.UserDetailsImpl;
import com.elogix.api.authentication.infrastructure.exception.JwtRefreshException;
import com.elogix.api.authentication.infrastructure.exception.JwtTokenException;
import com.elogix.api.authentication.infrastructure.repository.JwtTokenProvider;
import com.elogix.api.authentication.infrastructure.repository.token.TokenGatewayImpl;
import com.elogix.api.authentication.infrastructure.repository.userdetails.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenGatewayImpl tokenGateway; // Change from TokenUseCase
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader(jwtProperties.getHeader());
            if (authHeader == null || !authHeader.startsWith(jwtProperties.getPrefix() + " ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(7);

            if (!jwtTokenProvider.validateAccessToken(token)) {
                tokenGateway.revokeToken(token);
                throw new JwtTokenException(token, "Access token expired");
            }

            String username = jwtTokenProvider.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(username);

                // Token is already validated, no need to check again
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            handleAuthenticationError(response, ex);
        }
    }

    private void handleAuthenticationError(HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String errorCode = "AUTHENTICATION_ERROR";
        if (ex instanceof JwtTokenException) {
            errorCode = "ACCESS_TOKEN_EXPIRED";
        } else if (ex instanceof JwtRefreshException) {
            errorCode = "REFRESH_TOKEN_EXPIRED";
        }

        String error = new ObjectMapper().writeValueAsString(Map.of(
                "error", "Unauthorized",
                "message", ex.getMessage(),
                "code", errorCode));
        response.getWriter().write(error);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/api/v1/auth/authenticate") ||
                path.equals("/api/v1/auth/refresh") ||
                path.equals("/api/v1/auth/register") ||
                path.equals("/api/v1/auth/logout") ||
                path.equals("/actuator/health") ||
                path.equals("/ws") ||
                path.equals("/ws-plain") ||
                path.startsWith("/ws/") ||
                path.startsWith("/ws-plain/");
    }
}
