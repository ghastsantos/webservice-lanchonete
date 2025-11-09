package br.pucpr.lanchonete.security;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private String email;
    private Instant expiresAt;
}

