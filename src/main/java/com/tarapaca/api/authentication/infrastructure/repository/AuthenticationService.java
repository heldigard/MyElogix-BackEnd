package com.tarapaca.api.authentication.infrastructure.repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tarapaca.api.authentication.domain.model.ETokenType;
import com.tarapaca.api.authentication.domain.model.TokenPair;
import com.tarapaca.api.authentication.domain.model.UserDetailsImpl;
import com.tarapaca.api.authentication.domain.usecase.TokenUseCase;
import com.tarapaca.api.authentication.dto.AuthenticationRequest;
import com.tarapaca.api.authentication.dto.AuthenticationResponse;
import com.tarapaca.api.authentication.dto.RefreshRequest;
import com.tarapaca.api.authentication.dto.RegisterRequest;
import com.tarapaca.api.authentication.infrastructure.exception.JwtRefreshException;
import com.tarapaca.api.users.domain.model.ERole;
import com.tarapaca.api.users.domain.model.RoleModel;
import com.tarapaca.api.users.domain.model.UserModel;
import com.tarapaca.api.users.domain.usecase.RoleUseCase;
import com.tarapaca.api.users.domain.usecase.UserUseCase;
import com.tarapaca.api.users.infrastructure.helpers.EmailValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserUseCase userUseCase;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleUseCase roleUseCase;
    private final TokenUseCase tokenUseCase;

    public AuthenticationResponse register(RegisterRequest request) {
        validateNewUser(request);
        UserDetailsImpl userDetails = createUserDetails(request);
        UserModel userSaved = userUseCase.add(userDetails);

        TokenPair tokenPair = tokenUseCase.generateTokenPair(userDetails);

        return AuthenticationResponse.builder()
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .tokenType(ETokenType.BEARER)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.debug("Authenticating user: {}", request.getUsername());

        // Authenticate credentials and get UserDetails in one step
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        TokenPair tokenPair = tokenUseCase.generateTokenPair(userDetails);

        return AuthenticationResponse.builder()
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .tokenType(ETokenType.BEARER)
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) {
        log.debug("Processing refresh token request");
        try {
            // Delegate refresh token operation to TokenService
            return tokenUseCase.refreshToken(request);
        } catch (JwtRefreshException e) {
            log.error("Refresh token validation failed: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during token refresh: {}", e.getMessage());
            throw new JwtRefreshException(request.getRefreshToken(), "Token refresh failed");
        }
    }

    private void validateNewUser(RegisterRequest request) {
        UserModel existingUser = userUseCase.findByUsername(request.getUsername(), false);
        if (Optional.ofNullable(existingUser).isPresent()) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        existingUser = userUseCase.findByEmail(request.getEmail(), false);
        if (Optional.ofNullable(existingUser).isPresent()) {
            throw new RuntimeException("Error: Email is already in use!");
        }
    }

    private UserDetailsImpl createUserDetails(RegisterRequest request) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        String username = request.getUsername();
        if (username != null && !username.isEmpty()) {
            userDetails.setUsername(request.getUsername());
        } else {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        userDetails.setFirstName(request.getFirstName());
        userDetails.setLastName(request.getLastName());
        userDetails.setPassword(passwordEncoder.encode(request.getPassword()));

        String email = request.getEmail();
        if (email != null && EmailValidator.isValidEmail(email)) {
            userDetails.setEmail(request.getEmail());
        }

        Set<RoleModel> roles = request.getRoles().stream()
                .map(this::getRoleModel)
                .collect(Collectors.toSet());
        userDetails.setRoles(roles);

        return userDetails;
    }

    private RoleModel getRoleModel(String role) {
        return roleUseCase.findByName(ERole.valueOf(role.toUpperCase()), false);
    }
}
