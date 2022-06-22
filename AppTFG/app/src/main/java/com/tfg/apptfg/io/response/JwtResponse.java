package com.tfg.apptfg.io.response;
import java.util.UUID;

public class JwtResponse {
    private String token;
    final private String type = "Bearer";
    private UUID id;
    private String email;
    private String rol;

    public JwtResponse(String token, UUID id, String email, String rol) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.rol = rol;
    }

    public JwtResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
