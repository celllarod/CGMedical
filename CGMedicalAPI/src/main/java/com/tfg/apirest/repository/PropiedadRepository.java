package com.tfg.apirest.repository;

import com.tfg.apirest.entity.Propiedad;
import com.tfg.apirest.entity.PropiedadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropiedadRepository extends JpaRepository<Propiedad, PropiedadId> {
}