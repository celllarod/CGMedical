package com.tfg.apirest.function;

import com.tfg.apirest.view.FarmacoDetalleView;
import com.tfg.apirest.view.PropiedadView;
import com.tfg.apirest.view.PropiedadesView;
import com.tfg.apirest.entity.Farmaco;
import org.springframework.cglib.core.internal.Function;

import java.util.ArrayList;


public class FarmacoToFarmacoDetalleViewFunction implements Function<Farmaco, FarmacoDetalleView> {
    /** Constante Dosis Máxima */
    private static final String DOS_MAX = "DOS_MAX";
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
        propiedadDosisMaxima.ifPresent(dm -> propiedades.setDosisMaxima(PropiedadView.builder() //
                                                                        .id(dm.getId()) //
                                                                        .unidad(dm.getUnidad()) //
                                                                        .valor(dm.getValor()) //
                                                                        .build()));

        // Propiedades Presentación comercial
        var propiedadesPresentacion = new ArrayList<PropiedadView>();
        var presentaciones = f.getPropiedades().stream().filter( p -> //
                PRE.equals(p.getTipoPropiedad().getCodigo())).toList();
        presentaciones.forEach( pre -> propiedadesPresentacion.add( PropiedadView.builder()//
                                                                    .id(pre.getId()) //
                                                                    .unidad(pre.getUnidad()) //
                                                                    .unidad(pre.getUnidad()) //
                                                                    .valor(pre.getValor()) //
                                                                    .build()));
        if (!propiedadesPresentacion.isEmpty())
            propiedades.setPresentaciones(propiedadesPresentacion);

        farmaco.setPropiedades(propiedades);

        return farmaco;
    }
}
