package com.elogix.api.users.infrastructure.helpers;

import com.elogix.api.authentication.domain.model.UserDetailsImpl;
import com.elogix.api.users.domain.model.UserBasic;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user_basic.UserBasicData;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<UserBasicData> {
    private final UserBasicMapper userBasicMapper;

    @Override
    public Optional<UserBasicData> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserBasic userBasic = userBasicMapper.fromUserDetails((UserDetailsImpl) userDetails);
            UserBasicData userData = userBasicMapper.toData(userBasic);
            return Optional.of(userData);
        }
        return Optional.empty();
    }
}
