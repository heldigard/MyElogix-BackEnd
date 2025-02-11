package com.tarapaca.api.authentication.dto;

import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String documentNumber;
    private String email;
    private String password;
    private Set<String> roles;
}
