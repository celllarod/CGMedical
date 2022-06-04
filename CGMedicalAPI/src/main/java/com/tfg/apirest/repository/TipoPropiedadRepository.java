package com.tfg.apirest.repository;

import com.tfg.apirest.entity.TipoPropiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TipoPropiedadRepository extends JpaRepository<TipoPropiedad, UUID> {
    Optional<TipoPropiedad> getTipoPropiedadByCodigo(String codigo);
}