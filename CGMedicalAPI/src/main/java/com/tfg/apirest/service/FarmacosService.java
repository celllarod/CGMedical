package com.tfg.apirest.service;


import com.tfg.apirest.dto.FarmacoView;
import com.tfg.apirest.dto.PropiedadView;
import com.tfg.apirest.dto.TipoView;
import com.tfg.apirest.entity.Farmaco;
import com.tfg.apirest.repository.FarmacoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class FarmacosService {

    private  final FarmacoRepository farmacoRepository;

    public List<FarmacoView> findAllFarmacos () {

        var farmacos = farmacoRepository.findAll();

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

}
