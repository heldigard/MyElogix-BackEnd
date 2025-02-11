package com.tarapaca.api.users.application.config;

import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.user_basic.UserBasicData;
import com.tarapaca.api.users.infrastructure.helpers.AuditorAwareImpl;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserBasicMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfiguration {

    @Bean
    public AuditorAware<UserBasicData> auditorProvider(UserBasicMapper userMapper) {
        return new AuditorAwareImpl(userMapper);
    }
}
