package com.tfg.apirest.repository;

import com.tfg.apirest.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolRepository extends JpaRepository<Rol, UUID> {

    /**
     * Permite encontrar un rol en el sistema a través de su código
     *
     * @param codigo
     * @return rol
     */
    Optional<Rol> findByCodigo(String codigo);
}