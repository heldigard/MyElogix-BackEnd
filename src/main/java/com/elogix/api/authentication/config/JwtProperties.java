package com.elogix.api.authentication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {
  private String secret;
  private long expiration;
  private long refreshExpiration;
  private int clockSkew;
  private String header;
  private String prefix;
}
