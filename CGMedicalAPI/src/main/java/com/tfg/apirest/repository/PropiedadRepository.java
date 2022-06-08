package com.tfg.apirest.repository;

import com.tfg.apirest.entity.Propiedad;
import com.tfg.apirest.entity.PropiedadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface PropiedadRepository extends JpaRepository<Propiedad, PropiedadId> {

//    /** Permite eliminar un conjunto de propiedade*/
//    void deleteAllByUUID(Set<UUID> uuids);
}