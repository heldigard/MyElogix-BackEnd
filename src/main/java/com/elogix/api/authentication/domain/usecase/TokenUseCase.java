package com.elogix.api.authentication.domain.usecase;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.elogix.api.authentication.domain.gateway.TokenGateway;
import com.elogix.api.authentication.domain.model.TokenModel;
import com.elogix.api.authentication.domain.model.TokenPair;
import com.elogix.api.authentication.domain.model.UserDetailsImpl;
import com.elogix.api.authentication.dto.AuthenticationResponse;
import com.elogix.api.authentication.dto.RefreshRequest;
import com.elogix.api.users.domain.model.UserModel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenUseCase {
    private final TokenGateway gateway;

    public TokenModel add(TokenModel tokenModel) {
        return gateway.add(tokenModel);
    }

    public TokenModel update(TokenModel tokenModel) {
        return gateway.update(tokenModel);
    }

    public void deleteById(Long id) {
        gateway.deleteById(id);
    }

    public void deleteByToken(String token) {
        gateway.deleteByToken(token);
    }

    public void deleteByRefreshToken(byte[] refreshToken) {
        gateway.deleteByRefreshToken(refreshToken);
    }

    public List<TokenModel> findAll(
            List<Sort.Order> sortOrders,
            boolean isDeleted) {
        return gateway.findAll(sortOrders, isDeleted);
    }

    public TokenModel findById(Long id, boolean isDeleted) {
        return gateway.findById(id, isDeleted);
    }

    public TokenModel findByUserId(Long userId) {
        return gateway.findByUserId(userId);
    }

    public TokenModel findValidToken(Long userId) {
        return gateway.findValidToken(userId);
    }

    public void revokeAllUserTokens(Long userId) {
        gateway.revokeAllUserTokens(userId);
    }

    public void cleanExpiredTokens() {
        gateway.cleanExpiredTokens();
    }

    public Optional<TokenModel> findValidTokenByUser(Long userId) {
        return gateway.findValidTokenByUser(userId);
    }

    public TokenModel findByToken(byte[] token) {
        return gateway.findByToken(token);
    }

    public TokenModel findByRefreshToken(byte[] refreshToken) {
        return gateway.findByRefreshToken(refreshToken);
    }

    public void revokeToken(String token) {
        gateway.revokeToken(token);
    }

    public void revokeToken(UserModel user) {
        gateway.revokeToken(user);
    }

    public TokenPair generateTokenPair(UserDetailsImpl userDetails) {
        return gateway.generateTokenPair(userDetails);
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) {
        return gateway.refreshToken(request);
    }
}
