package com.tfg.apirest.repository;

import com.tfg.apirest.entity.Farmaco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FarmacoRepository extends JpaRepository<Farmaco, UUID> {
    /**
     * Permite obtener el listado de fármacos asociados a un hospital
     * @param idHospital UUID del Hospital del que obtener los fármacos
     * @return listado de fármacos asociados
     */
    List<Farmaco> findAllByHospital_Id(UUID idHospital);
}