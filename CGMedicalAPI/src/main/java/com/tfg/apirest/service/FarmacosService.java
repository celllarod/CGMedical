package com.tfg.apirest.service;


import com.tfg.apirest.dto.FarmacoView;
import com.tfg.apirest.dto.PropiedadView;
import com.tfg.apirest.dto.TipoView;
import com.tfg.apirest.entity.Farmaco;
import com.tfg.apirest.repository.FarmacoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
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

    public List<FarmacoView> findAllFarmacos () {

        // Se obtiene el usuario de la sesión
        var usuario = usuariosService.getUsuarioSesion();

        // Se obtiene el listado de fármacos del hospital al que está asociado el usuario
        var farmacos = this.findAllFarmacoByHospital(usuario.getHospital().getId());

        var farmacosView = new ArrayList<FarmacoView>();

        farmacos.forEach( f -> {
            var farmaco = new FarmacoView();
            farmaco.setId(f.getId());
            farmaco.setNombre(f.getNombre());
            farmaco.setHospital(f.getHospital().getNombre());
            f.getPropiedades().forEach( p -> {
                var propiedad = new PropiedadView();
                var tipo = new TipoView(p.getPropiedad().getCodigo(), p.getPropiedad().getDescripcion());
                propiedad.setTipo(tipo);
                propiedad.setUnidad(p.getUnidad());
                propiedad.setValor(p.getValor());
                farmaco.getPropiedades().add(propiedad);
            });
            farmacosView.add(farmaco);
        });

        return farmacosView;
    }

    /**
     * Permite obtener el listado de fármacos de un hospital
     *
     */
    private List<Farmaco> findAllFarmacoByHospital(UUID idHospital) {
        return farmacoRepository.findAllByHospital_Id(idHospital);
    }

}
