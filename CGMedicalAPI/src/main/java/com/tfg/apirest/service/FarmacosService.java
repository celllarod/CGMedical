package com.tfg.apirest.service;


import com.tfg.apirest.dto.FarmacoDetalleView;
import com.tfg.apirest.dto.FarmacoResumenView;
import com.tfg.apirest.entity.Farmaco;
import com.tfg.apirest.function.FarmacoToFarmacoDetalleViewFunction;
import com.tfg.apirest.function.FarmacoToFarmacoResumenViewFunction;
import com.tfg.apirest.repository.FarmacoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class FarmacosService {

    /** Repositorio de Fármacos */
    private  final FarmacoRepository farmacoRepository;
    /** Servicio de usuarios */
    @Lazy
    private final UsuariosService usuariosService;

    /**
     * Permite obtener el listado de fármacos del hospital de la sesión actual con la información principal
     *
     * @return listado de fármacos
     */
    public List<FarmacoResumenView> findAllFarmacos() {

        // Se obtiene el usuario de la sesión
        var usuario = usuariosService.getUsuarioSesion();

        // Se obtiene el listado de fármacos del hospital al que está asociado el usuario
        var farmacos = this.findAllFarmacoByHospital(usuario.getHospital().getId());
        var farmacosView = new ArrayList<FarmacoResumenView>();
        farmacos.forEach( f -> {
            var farmaco = new FarmacoToFarmacoResumenViewFunction().apply(f);
            farmacosView.add(farmaco);
        });

        return farmacosView;
    }

    /**
     * Permite obtener un fármaco a través de su identificador
     *
     * @param idFarmaco Identificador del fármaco
     * @return  fármaco
     */
    public FarmacoDetalleView obtenerFarmaco(UUID idFarmaco) {
         return new FarmacoToFarmacoDetalleViewFunction().apply(this.getFarmacoById(idFarmaco));
    }

    /**
     * Consulta para obtener el listado de fármacos de un hospital
     *
     * @param idHospital Identificador del hospital
     */
    private List<Farmaco> findAllFarmacoByHospital(UUID idHospital) {
        return farmacoRepository.findAllByHospital_Id(idHospital);
    }

    /**
     * Consulta para obtener un fármaco a través de su ID
     *
     * @param idFarmaco Identificador del fármaco
     * @return fármaco
     */
    private Farmaco getFarmacoById(UUID idFarmaco) {
        return farmacoRepository.getFarmacoById(idFarmaco) //
         .orElseThrow(() -> new NoSuchElementException("[ERROR] No se ha encontrado el Fármaco con el ID especificado: " + idFarmaco));
    }

}
