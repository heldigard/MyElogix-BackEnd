package com.elogix.api.authentication.infrastructure.repository.userdetails;

import com.elogix.api.authentication.domain.model.UserDetailsImpl;
import com.elogix.api.users.domain.model.UserModel;
import com.elogix.api.users.domain.usecase.UserUseCase;
import com.elogix.api.users.infrastructure.helpers.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserUseCase userUseCase;
    private final UserMapper userMapper;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userUseCase.findByUsername(username, false);
        if (Optional.ofNullable(userModel).isEmpty()) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return userMapper.toUserDetails(userModel);
    }
}
