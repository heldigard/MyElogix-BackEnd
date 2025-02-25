package com.elogix.api.shared.infraestructure.driven_adapters;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;

import com.elogix.api.shared.application.config.SecurityProperties;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorsService {
        private final SecurityProperties securityProperties;

        public CorsConfiguration createCorsConfiguration(HttpServletRequest httpServletRequest) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowCredentials(true);
                configuration.setMaxAge(securityProperties.getMaxAge());
                configuration.setAllowedOrigins(securityProperties.getAllowedOrigins());
                configuration.setAllowedHeaders(Arrays.asList(
                                HttpHeaders.AUTHORIZATION,
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaders.ACCEPT,
                                HttpHeaders.ORIGIN));
                configuration.setExposedHeaders(Arrays.asList(
                                HttpHeaders.AUTHORIZATION,
                                HttpHeaders.CONTENT_TYPE));
                configuration.setAllowedMethods(Arrays.asList(
                                HttpMethod.GET.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.DELETE.name(),
                                HttpMethod.OPTIONS.name()));
                return configuration;
        }
}
