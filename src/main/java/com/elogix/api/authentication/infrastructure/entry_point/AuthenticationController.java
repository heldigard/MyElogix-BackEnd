package com.elogix.api.authentication.infrastructure.entry_point;

import java.util.Base64;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.authentication.domain.usecase.TokenUseCase;
import com.elogix.api.authentication.dto.AuthenticationRequest;
import com.elogix.api.authentication.dto.AuthenticationResponse;
import com.elogix.api.authentication.dto.RefreshRequest;
import com.elogix.api.authentication.infrastructure.exception.JwtAuthenticateException;
import com.elogix.api.authentication.infrastructure.repository.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final TokenUseCase tokenUseCase;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request) {
        try {
            // Decodifica el password desde Base64
            String decodedPassword = new String(Base64.getDecoder().decode(request.getPassword()));

            // Crea una nueva request con el password decodificado
            AuthenticationRequest decodedRequest = new AuthenticationRequest(
                    request.getUsername(),
                    decodedPassword);

            return ResponseEntity.ok(service.authenticate(decodedRequest));
        } catch (AuthenticationException e) {
            throw new JwtAuthenticateException(request.getUsername(), "Invalid credentials");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(
            @Valid @RequestBody RefreshRequest request) {
        return ResponseEntity.ok(tokenUseCase.refreshToken(request));
    }

    @GetMapping("/live")
    public ResponseEntity<Boolean> live() {
        return ResponseEntity.ok(true);
    }

    @GetMapping(value = "/encodedPassword")
    public ResponseEntity<String> encodedPassword(
            @RequestParam String password) {
        return ResponseEntity.ok(passwordEncoder.encode(password));
    }
}
