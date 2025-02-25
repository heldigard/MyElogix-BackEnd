package com.elogix.api.authentication.infrastructure.repository.token;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.elogix.api.authentication.domain.gateway.TokenGateway;
import com.elogix.api.authentication.domain.model.ETokenType;
import com.elogix.api.authentication.domain.model.TokenModel;
import com.elogix.api.authentication.domain.model.TokenPair;
import com.elogix.api.authentication.domain.model.UserDetailsImpl;
import com.elogix.api.authentication.domain.model.UserDetailsWithToken;
import com.elogix.api.authentication.dto.AuthenticationResponse;
import com.elogix.api.authentication.dto.RefreshRequest;
import com.elogix.api.authentication.infrastructure.exception.JwtAuthenticateException;
import com.elogix.api.authentication.infrastructure.exception.JwtRefreshException;
import com.elogix.api.authentication.infrastructure.helpers.TokenMapper;
import com.elogix.api.authentication.infrastructure.repository.JwtTokenProvider;
import com.elogix.api.authentication.infrastructure.repository.userdetails.UserDetailsServiceImpl;
import com.elogix.api.generics.config.GenericConfig;
import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericGatewayImpl;
import com.elogix.api.users.domain.model.UserModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenGatewayImpl extends GenericGatewayImpl<TokenModel, TokenData, TokenDataJpaRepository, TokenMapper>
        implements TokenGateway {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    public TokenGatewayImpl(GenericConfig<TokenModel, TokenData, TokenDataJpaRepository, TokenMapper> config,
            JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        super(config);
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void deleteByToken(String token) {
        repository.deleteByToken(token.getBytes());
    }

    @Override
    public void deleteByRefreshToken(byte[] refreshToken) {
        repository.deleteByRefreshToken(refreshToken);
    }

    @Override
    public TokenModel findByUserId(Long userId) {
        Optional<TokenData> tokenData = repository.findByUserId(userId);
        return mapper.toDomain(tokenData.orElse(null));
    }

    public TokenModel findByToken(byte[] accessToken) {
        Optional<TokenData> tokenData = repository.findByToken(accessToken);
        return mapper.toDomain(tokenData.orElse(null));
    }

    @Override
    public TokenModel findByRefreshToken(byte[] refreshToken) {
        // Use Base64.encode() for consistent byte array comparison
        Optional<TokenData> tokenData = repository.findByRefreshToken(refreshToken);
        return mapper.toDomain(tokenData.orElse(null));
    }

    @Override
    public TokenModel findValidToken(Long userId) {
        Optional<TokenData> tokenData = repository.findValidToken(userId);
        return mapper.toDomain(tokenData.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public void revokeToken(UserModel user) {
        TokenModel token = findValidToken(user.getId());
        if (token.getId() != null) {
            token.setRevoked(true);
            update(token);
        }
    }

    @Override
    public void revokeToken(String token) {
        TokenModel tokenModel = findByToken(token.getBytes());
        if (tokenModel != null && tokenModel.getId() != null) {
            tokenModel.setRevoked(true);
            tokenModel.setToken(new byte[0]);
            tokenModel.setRefreshToken(new byte[0]); // Empty byte array instead of empty string
            update(tokenModel);
        }
    }

    @Override
    public void revokeAllUserTokens(Long userId) {
        Optional<TokenData> tokens = repository.findByUserId(userId);
        tokens.ifPresent(token -> {
            token.setRevoked(true);
            repository.save(token);
        });
    }

    @Override
    public void cleanExpiredTokens() {
        Instant now = Instant.now();
        List<TokenData> expiredTokens = repository.findAll().stream()
                .filter(token -> token.getUpdatedAt().isBefore(now.minus(7, ChronoUnit.DAYS)))
                .toList();

        repository.deleteAll(expiredTokens);
    }

    @Override
    public Optional<TokenModel> findValidTokenByUser(Long userId) {
        Optional<TokenData> tokenData = repository.findValidToken(userId);
        return tokenData.map(mapper::toDomain);
    }

    @Transactional
    public TokenPair generateTokenPair(UserDetailsImpl userDetails) {
        String accessToken = jwtTokenProvider.generateToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        // First try to find existing token
        TokenModel existingToken = findByUserId(userDetails.getId());
        TokenModel tokenToSave;

        if (existingToken != null) {
            // Update existing token
            tokenToSave = existingToken.toBuilder()
                    .token(accessToken.getBytes())
                    .refreshToken(refreshToken.getBytes())
                    .isRevoked(false)
                    .updatedAt(Instant.now())
                    .build();

            // Preserve existing version
            tokenToSave.setVersion(existingToken.getVersion());
        } else {
            // Create new token
            tokenToSave = TokenModel.builder()
                    .user(userDetails)
                    .token(accessToken.getBytes())
                    .refreshToken(refreshToken.getBytes())
                    .type(ETokenType.BEARER)
                    .createdAt(Instant.now())
                    .isRevoked(false)
                    .version(0L) // Initialize version for new token
                    .build();
        }

        // Use single save method instead of separate add/update
        add(tokenToSave);

        return new TokenPair(accessToken, refreshToken);
    }

    @Transactional
    public TokenModel add(TokenModel token) {
        TokenData tokenData = mapper.toData(token);
        TokenData savedData = repository.save(tokenData);
        return mapper.toDomain(savedData);
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) {
        log.debug("Attempting to refresh token: {}", request.getRefreshToken());

        // Pass raw token for validation
        UserDetailsWithToken validated = validateRefreshToken(request.getRefreshToken());
        log.debug("Token validated successfully for user: {}", validated.userDetails().getUsername());

        TokenPair tokenPair = generateTokenPair(validated.userDetails());
        log.debug("Generated new token pair for user: {}", validated.userDetails().getUsername());

        return AuthenticationResponse.builder()
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .tokenType(ETokenType.BEARER)
                .build();
    }

    public UserDetailsWithToken validateRefreshToken(String refreshToken) {
        // Validate token format and expiry
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new JwtRefreshException(refreshToken, "Invalid refresh token");
        }

        // Get stored token
        TokenModel tokenModel = findByRefreshToken(refreshToken.getBytes());
        if (tokenModel == null || tokenModel.isRevoked()) {
            throw new JwtRefreshException(refreshToken, "Token revoked or not found");
        }

        // Get and validate user in one step
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(
                jwtTokenProvider.extractUsername(refreshToken));
        validateUserStatus(userDetails);

        return new UserDetailsWithToken(userDetails, tokenModel);
    }

    public UserDetailsImpl validateAndGetUser(String token) {
        String username = jwtTokenProvider.extractUsername(token);
        if (username == null) {
            throw new JwtAuthenticateException(token, "Username inválido");
        }

        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(username);
        validateUserStatus(userDetails);

        return userDetails;
    }

    public void validateUserStatus(UserDetailsImpl user) {
        log.debug("Validating user status: enabled={}, accountNonLocked={}",
                user.isEnabled(), user.isAccountNonLocked());

        if (!user.isEnabled()) {
            throw new JwtAuthenticateException(null, "Usuario deshabilitado");
        }

        if (!user.isAccountNonLocked()) {
            throw new JwtAuthenticateException(null, "Cuenta bloqueada");
        }
    }

}
