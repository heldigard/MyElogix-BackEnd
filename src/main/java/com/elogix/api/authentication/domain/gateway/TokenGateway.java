package com.elogix.api.authentication.domain.gateway;

import java.util.Optional;

import com.elogix.api.authentication.domain.model.TokenModel;
import com.elogix.api.authentication.domain.model.TokenPair;
import com.elogix.api.authentication.domain.model.UserDetailsImpl;
import com.elogix.api.authentication.dto.AuthenticationResponse;
import com.elogix.api.authentication.dto.RefreshRequest;
import com.elogix.api.generics.domain.gateway.GenericGateway;
import com.elogix.api.users.domain.model.UserModel;

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
