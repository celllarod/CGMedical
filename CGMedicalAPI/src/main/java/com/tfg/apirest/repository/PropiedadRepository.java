package com.tfg.apirest.repository;

import com.tfg.apirest.entity.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropiedadRepository extends JpaRepository<Propiedad, UUID> {
}