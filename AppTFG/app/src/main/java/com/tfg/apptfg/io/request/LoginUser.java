package com.tfg.apptfg.io.request;

/** Clase que modela el cuerpo de la petición para loguear a un usuario */

public class LoginUser {
    /** Email del usuario */
    private String email;
    /** Contraseña del usuario */
    private String password;

    public LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginUser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
