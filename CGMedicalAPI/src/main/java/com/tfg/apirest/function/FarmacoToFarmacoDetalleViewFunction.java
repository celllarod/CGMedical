package com.tfg.apirest.function;

import com.tfg.apirest.dto.FarmacoDetalleView;
import com.tfg.apirest.dto.PropiedadView;
import com.tfg.apirest.dto.PropiedadesView;
import com.tfg.apirest.entity.Farmaco;
import org.springframework.cglib.core.internal.Function;

import java.util.ArrayList;


public class FarmacoToFarmacoDetalleViewFunction implements Function<Farmaco, FarmacoDetalleView> {
    /** Constante Dosis Máxima */
    private static final String DOS_MAX = "DOS_MAX";
    /** Constante Concentración */
    private static final String CON = "CON";
    /** Constante Presentación comercial */
    private static final String PRE = "PRE";

    @Override
    public FarmacoDetalleView apply(Farmaco f) {

        var farmaco = new FarmacoDetalleView();
        farmaco.setId(f.getId());
        farmaco.setNombre(f.getNombre());
        var propiedades = new PropiedadesView();

        // Propiedad dosis máxima
        var propiedadDosisMaxima = f.getPropiedades().stream().filter( p -> //
                DOS_MAX.equals(p.getTipoPropiedad().getCodigo())).findFirst();
        propiedadDosisMaxima.ifPresent(dm -> propiedades.setDosisMaxima(PropiedadView.builder()//
                                                                        .unidad(dm.getId().getUnidad()) //
                                                                        .valor(dm.getId().getValor()) //
                                                                        .build()));

        // Propiedades Presentación comercial
        var propiedadesPresentacion = new ArrayList<PropiedadView>();
        var presentaciones = f.getPropiedades().stream().filter( p -> //
                PRE.equals(p.getTipoPropiedad().getCodigo())).toList();
        presentaciones.forEach( pre -> propiedadesPresentacion.add( PropiedadView.builder()//
                                                                     .unidad(pre.getId().getUnidad()) //
                                                                     .valor(pre.getId().getValor()) //
                                                                     .build()));
        if (!propiedadesPresentacion.isEmpty())
            propiedades.setPresentaciones(propiedadesPresentacion);

        // Propiedades Concentración
        var propiedadesConcentracion = new ArrayList<PropiedadView>();
        var concentraciones = f.getPropiedades().stream().filter( p -> //
                CON.equals(p.getTipoPropiedad().getCodigo())).toList();
        concentraciones.forEach( con -> propiedadesConcentracion.add( PropiedadView.builder()//
                                                                        .unidad(con.getId().getUnidad()) //
                                                                        .valor(con.getId().getValor()) //
                                                                        .build()));
        if (!propiedadesConcentracion.isEmpty())
            propiedades.setPresentaciones(propiedadesConcentracion);

        farmaco.setPropiedades(propiedades);

        return farmaco;
    }
}
