package com.tfg.apirest.repository;
import com.tfg.apirest.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    /**
     * Permite obtener el listado con los nombres de todos los hospitales existentes en el sistema
     *
     * @return listado hospitales
     */
    @Query ("SELECT h.nombre FROM Hospital h")
    List<String> findNombres();
}