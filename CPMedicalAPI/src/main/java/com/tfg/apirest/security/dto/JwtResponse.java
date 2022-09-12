package com.tfg.apirest.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    final private String type = "Bearer";
    private UUID id;
    private String email;
    private String rol;
}
