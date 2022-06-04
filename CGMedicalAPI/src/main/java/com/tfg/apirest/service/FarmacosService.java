package com.tfg.apirest.service;


import com.tfg.apirest.dto.FarmacoDetalleView;
import com.tfg.apirest.dto.FarmacoResumenView;
import com.tfg.apirest.entity.Farmaco;
import com.tfg.apirest.entity.Propiedad;
import com.tfg.apirest.function.FarmacoToFarmacoDetalleViewFunction;
import com.tfg.apirest.function.FarmacoToFarmacoResumenViewFunction;
import com.tfg.apirest.repository.FarmacoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@RequiredArgsConstructor
@Validated
public class FarmacosService {

    /** Repositorio de Fármacos */
    private  final FarmacoRepository farmacoRepository;
    /** Servicio de usuarios */
    @Lazy
    private final UsuariosService usuariosService;

    /** Servicio de Tipo Propiedad */
    @Lazy
    private final TipoPropiedadesService tipoPropiedadesService;
    /** Constante Dosis Máxima */
    private static final String DOS_MAX = "DOS_MAX";
    /** Constante Presentación comercial */
    private static final String PRE = "PRE";

    /**
     * Permite obtener el listado de fármacos del hospital del usuario de la sesión actual con la información principal
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
     * Permite crear un Fármaco y añadirlo al listado de fármacos del hospital asociado al usuario
     *
     * @param farmaco Datos con el fármaco a añadir
     * @return fármaco creado
     */
    @Transactional
    public FarmacoDetalleView crearFarmaco(Farmaco farmaco, Set<Propiedad> propiedades) {
        // TODO: controlar nombre de fármaco no existe en ese hospital
        // Se obtiene el usuario de la sesión
        var usuario = usuariosService.getUsuarioSesion();

        // Se obtiene el hospital al que está asociado el usuario
        farmaco.setHospital(usuario.getHospital());

        // Se setan las propiedades del fármaco
        var tipoDosisMaxima = tipoPropiedadesService.obtenerPropiedad(DOS_MAX);
        var tipoPresentacion = tipoPropiedadesService.obtenerPropiedad(PRE);
        propiedades.forEach( p -> {
            //p.getId().setCdFarmaco(farmacoGuardado.getId());
           // p.setFarmaco(farmacoGuardado);
            if (DOS_MAX.equals(p.getId().getCdPropiedad()))
                p.setTipoPropiedad(tipoDosisMaxima);
            if (PRE.equals(p.getId().getCdPropiedad()))
                p.setTipoPropiedad(tipoPresentacion);
        });
        farmaco.setPropiedades(propiedades);

        // Se guarda el fármaco
        var farmacoGuardado = this.saveFarmaco(farmaco);

        return new FarmacoToFarmacoDetalleViewFunction().apply(farmacoGuardado);
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


    /**
     * Permite crear un fármaco
     *
     * @param farmaco Datos con el fármaco a añadir
     * @return fármaco creado
     */
    private Farmaco saveFarmaco(Farmaco farmaco) {
        return farmacoRepository.saveAndFlush(farmaco);
    }
}
