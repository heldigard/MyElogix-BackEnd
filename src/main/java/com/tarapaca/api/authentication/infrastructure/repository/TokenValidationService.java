package com.tarapaca.api.authentication.infrastructure.repository;

import org.springframework.stereotype.Service;

import com.tarapaca.api.authentication.domain.model.TokenModel;
import com.tarapaca.api.authentication.domain.model.UserDetailsImpl;
import com.tarapaca.api.authentication.domain.model.UserDetailsWithToken;
import com.tarapaca.api.authentication.domain.usecase.TokenUseCase;
import com.tarapaca.api.authentication.infrastructure.exception.JwtAuthenticateException;
import com.tarapaca.api.authentication.infrastructure.exception.JwtRefreshException;
import com.tarapaca.api.authentication.infrastructure.repository.userdetails.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenValidationService {
  private final TokenUseCase tokenUseCase;
  private final JwtTokenProvider jwtTokenProvider;



}
