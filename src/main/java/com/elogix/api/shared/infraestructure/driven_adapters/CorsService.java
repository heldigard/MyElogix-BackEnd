package com.elogix.api.shared.infraestructure.driven_adapters;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CorsService {
    private static final Long MAX_AGE = 3600L;

    public CorsConfiguration createCorsConfiguration(HttpServletRequest httpServletRequest) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(MAX_AGE);
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200",
                "http://10.0.5.200:4200",
                "https://app.moldurastarapaca.com",
                "https://dev.app.moldurastarapaca.com"
        ));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList(
//                HttpHeaders.AUTHORIZATION,
//                HttpHeaders.CONTENT_TYPE,
//                HttpHeaders.ACCEPT,
//                HttpHeaders.ORIGIN
//                HttpHeaders.CONTENT_LENGTH,
//                HttpHeaders.ACCEPT_ENCODING,
//                HttpHeaders.ACCEPT_LANGUAGE,
//                HttpHeaders.CACHE_CONTROL,
//                HttpHeaders.USER_AGENT,
//                HttpHeaders.REFERER,
//                HttpHeaders.UPGRADE_INSECURE_REQUESTS,
//                HttpHeaders.HOST,
//                HttpHeaders.CONNECTION,
//                HttpHeaders.DNT,
//                HttpHeaders.X_REQUESTED_WITH,
//                HttpHeaders.X_FORWARDED_FOR,
//                HttpHeaders.X_FORWARDED_PROTO,
//                HttpHeaders.X_FORWARDED_PORT,
//                HttpHeaders.X_FORWARDED_HOST,
//                HttpHeaders.X_FORWARDED_PREFIX,
//                HttpHeaders.ACCEPT_CHARSET,
//                HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD,
//                HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS,
//                HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
//                HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,
//                HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
//                HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
//                HttpHeaders.ACCESS_CONTROL_MAX_AGE,
//                HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
//                HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
//                HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
//                HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,
//                HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
//        ));
        configuration.setAllowedMethods(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList(
//                HttpMethod.GET.name(),
//                HttpMethod.POST.name(),
//                HttpMethod.PUT.name(),
//                HttpMethod.DELETE.name(),
//                HttpMethod.OPTIONS.name()
//        ));
        return configuration;
    }
}
