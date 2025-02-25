package com.elogix.api.shared.application.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.cors")
public class SecurityProperties {
  private List<String> allowedOrigins;
  private Long maxAge;
}
