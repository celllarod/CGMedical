package com.tfg.apirest.service;


import com.tfg.apirest.entity.Usuario;
import com.tfg.apirest.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Validated
public class UsuariosService {

    /** Repositorio de Usuario */
    private final UsuarioRepository usuarioRepository;

    /**
     * Permite obtener usuario asociado a la sesión
     *
     * @return usuario
     */
    protected Usuario getUsuarioSesion() {
        var userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getUsuarioByEmail(userDetails.getUsername());

    }

    /**
     * Permite registrar un usuario
     *
     * @param usuario Usuario a registrar
     * @return usuario
     */
    protected Usuario registrar(Usuario usuario){
        return this.save(usuario);
    }


    /** Permite saber si un usuario existe en el sistema a través de su email
     *
     * @param email Email de usuario
     * @return true si existe
     */
    public boolean existsUsuarioByEmail(String email){
        return this.existsByEmail(email);
    }

    /**
     * Permite obtener un usuario a través de su email
     *
     * @param email Email de usuario
     * @return Usuario
     */
    private Usuario getUsuarioByEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el usuario con el email especificado: " + email));
    }

    /** Comprueba si esiste un usuario en el sistema a través de su email
     *
     * @param email Email de usuario
     * @return true si existe
     */
    private boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    /**
     * Rgistra un usuario
     *
     * @param usuario Usuario a registrar
     * @return usuario
     */
    private Usuario save(Usuario usuario){
        return usuarioRepository.saveAndFlush(usuario);
    }
}
