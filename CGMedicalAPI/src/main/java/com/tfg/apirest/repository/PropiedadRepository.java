package com.tfg.apirest.repository;

import com.tfg.apirest.entity.Propiedad;
import com.tfg.apirest.entity.PropiedadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface PropiedadRepository extends JpaRepository<Propiedad, PropiedadId> {

}