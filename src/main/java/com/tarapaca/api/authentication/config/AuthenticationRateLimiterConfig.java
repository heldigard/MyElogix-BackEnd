package com.tarapaca.api.authentication.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;

@Configuration
public class AuthenticationRateLimiterConfig {
  @Bean
  public RateLimiter authenticationRateLimiter() {
    RateLimiterConfig config = RateLimiterConfig.custom()
        .limitForPeriod(5)
        .limitRefreshPeriod(Duration.ofMinutes(15))
        .timeoutDuration(Duration.ofSeconds(15))
        .build();

    return RateLimiter.of("authenticationRateLimiter", config);
  }
}
