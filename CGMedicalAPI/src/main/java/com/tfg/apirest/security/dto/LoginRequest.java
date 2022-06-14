package com.tfg.apirest.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
    /** Email del usuario */
    @JsonProperty("email")
    @NotNull(message = "El campo 'email' es obligatorio.")
    @NotBlank(message = "El campo 'email' no puede ir vacío.")
    @Email(message = "El formato del campo 'email' es incorrecto")
    private String email;
    /** Contraseña del usuario */
    @JsonProperty("password")
    @NotNull(message = "El campo 'password' es obligatorio.")
    @NotBlank(message = "El campo 'password' no puede ir vacío.")
    private String password;
}
