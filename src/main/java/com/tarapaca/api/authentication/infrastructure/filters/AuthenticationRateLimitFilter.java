package com.tarapaca.api.authentication.infrastructure.filters;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationRateLimitFilter extends OncePerRequestFilter {
  private final RateLimiter rateLimiter;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (isAuthenticationEndpoint(request)) {
      try {
        rateLimiter.acquirePermission();
        filterChain.doFilter(request, response);
      } catch (RequestNotPermitted e) {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType("application/json");
        response.getWriter()
            .write("{\"statusCode\": 429, \"message\": \"Too many authentication attempts. Please try again later.\"}");
        return;
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }

  private boolean isAuthenticationEndpoint(HttpServletRequest request) {
    return request.getRequestURI().equals("/api/v1/auth/authenticate")
        && request.getMethod().equals("POST");
  }
}
