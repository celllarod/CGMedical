package com.tfg.apirest.repository;

import com.tfg.apirest.entity.FarmacoPropiedad;
import com.tfg.apirest.entity.FarmacoPropiedadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmacoPropiedadRepository extends JpaRepository<FarmacoPropiedad, FarmacoPropiedadId> {
}