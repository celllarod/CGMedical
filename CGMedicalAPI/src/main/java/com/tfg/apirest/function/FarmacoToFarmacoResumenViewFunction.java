package com.tfg.apirest.function;

import com.tfg.apirest.view.FarmacoResumenView;
import com.tfg.apirest.view.PropiedadView;
import com.tfg.apirest.entity.Farmaco;
import org.springframework.cglib.core.internal.Function;

public class FarmacoToFarmacoResumenViewFunction implements Function<Farmaco, FarmacoResumenView> {
/** Constante Dosis Máxima */
private static final String DOS_MAX = "DOS_MAX";

    @Override
    public FarmacoResumenView apply(Farmaco f) {

        var farmaco = new FarmacoResumenView();
        farmaco.setId(f.getId());
        farmaco.setNombre(f.getNombre());
        var propiedadDosisMaxima = f.getPropiedades().stream().filter( p -> //
                DOS_MAX.equals(p.getTipoPropiedad().getCodigo())).findFirst();
        propiedadDosisMaxima.ifPresent(dm -> farmaco.setDosisMaxima(PropiedadView.builder()//
                .unidad(dm.getUnidad()) //
                .valor(dm.getValor()) //
                .build()));
        return farmaco;
    }
}
