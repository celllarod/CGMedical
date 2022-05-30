package com.tfg.apirest.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido1;

    private String apellido2;
    @NotBlank
    @Email
    @Size(max=255)
    private String email;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String rol;

}
