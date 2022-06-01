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
     * Permite obtener un usuario a través de su email
     *
     * @param email Email de usuario
     * @return Usuario
     */
    private Usuario getUsuarioByEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("[ERROR] No se ha encontrado el Usuario con el email especificado."));
    }
}
