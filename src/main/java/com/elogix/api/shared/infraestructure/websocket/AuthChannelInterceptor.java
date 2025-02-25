package com.elogix.api.shared.infraestructure.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.elogix.api.authentication.infrastructure.exception.JwtTokenException;
import com.elogix.api.authentication.infrastructure.repository.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthChannelInterceptor implements ChannelInterceptor {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
      String token = accessor.getFirstNativeHeader("Authorization");
      if (token != null && token.startsWith("Bearer ")) {
        token = token.substring(7);
        // Usamos validateAccessToken porque solo queremos permitir tokens de acceso
        if (jwtTokenProvider.validateAccessToken(token)) {
          try {
            String username = jwtTokenProvider.extractUsername(token);
            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, null);
            accessor.setUser(auth);
          } catch (JwtTokenException e) {
            log.error("Error processing WebSocket connection: {}", e.getMessage());
            return null; // Rechazar la conexión
          }
        }
      }
    }
    return message;
  }
}
