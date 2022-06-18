package com.tfg.apirest.repository;

import com.tfg.apirest.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface HospitalRepository extends JpaRepository<Hospital, UUID> {

    /**
     * Permite obtener un hospital  a través de su nombre
     *
     * @param nombre Nombre del hospital
     * @return hospital
     */
    Optional<Hospital> findByNombre(String nombre);

}