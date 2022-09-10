package com.tfg.apirest.repository;

import com.tfg.apirest.entity.Farmaco;
import com.tfg.apirest.entity.Propiedad;
import com.tfg.apirest.view.FarmacoSimpleView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface FarmacoRepository extends JpaRepository<Farmaco, UUID>, CustomRepository<Farmaco> {
    /**
     * Permite obtener el listado de fármacos asociados a un hospital
     * @param idHospital UUID del Hospital del que obtener los fármacos
     * @return listado de fármacos asociados
     */
    List<Farmaco> findAllByHospital_Id(UUID idHospital);

    /**
     * Permite obtener un fármaco a través de su identificador
     *
     * @param idFarmaco Identificador del fármaco
     * @return fármaco
     */
    Optional<Farmaco> getFarmacoById(UUID idFarmaco);

    /**
     * Permite obtener el listado de nombres de fármacos asociados a un hospital
     * @param idHospital UUID del Hospital del que obtener los fármacos
     * @return listado de fármacos asociados
     */
    @Query ("SELECT new com.tfg.apirest.view.FarmacoSimpleView(id, nombre) from Farmaco where hospital.id=:idHospital")
    List<FarmacoSimpleView> findNombresFarmacosByHospital(UUID idHospital);
}