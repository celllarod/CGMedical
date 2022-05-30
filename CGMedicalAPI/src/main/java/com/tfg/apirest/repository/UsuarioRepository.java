package com.tfg.apirest.repository;

import com.tfg.apirest.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {


    /**
     * Permite encontrar un usuario a través de su email
     *
     * @param email
     * @return usuario
     */
    Optional<Usuario> findByEmail(String email);
    /**
     * Permite ver si existe un usuario en el sistema a treavés de su email
     *
     * @param email
     * @return true si existe el usuario
     */
    Boolean existsByEmail(String email);

}