package com.tarapaca.api.authentication.domain.gateway;

import java.util.Optional;

import com.tarapaca.api.authentication.domain.model.TokenModel;
import com.tarapaca.api.authentication.domain.model.TokenPair;
import com.tarapaca.api.authentication.domain.model.UserDetailsImpl;
import com.tarapaca.api.authentication.dto.AuthenticationResponse;
import com.tarapaca.api.authentication.dto.RefreshRequest;
import com.tarapaca.api.generics.domain.gateway.GenericGateway;
import com.tarapaca.api.users.domain.model.UserModel;

public interface TokenGateway extends GenericGateway<TokenModel> {
    void deleteByToken(String token);

    void deleteByRefreshToken(byte[] refreshToken);

    TokenModel findByUserId(Long userId);

    TokenModel findByToken(byte[] token);

    TokenModel findByRefreshToken(byte[] refreshToken);

    TokenModel findValidToken(Long userId);

    void revokeToken(UserModel user);

    void revokeToken(String token);

    void revokeAllUserTokens(Long userId);

    void cleanExpiredTokens();

    Optional<TokenModel> findValidTokenByUser(Long userId);

    TokenPair generateTokenPair(UserDetailsImpl userDetails);

    AuthenticationResponse refreshToken(RefreshRequest request);
}
