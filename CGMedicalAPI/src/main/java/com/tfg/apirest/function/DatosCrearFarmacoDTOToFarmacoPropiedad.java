package com.tfg.apirest.function;

import com.tfg.apirest.dto.DatosCrearFarmacoDTO;
import com.tfg.apirest.entity.Propiedad;
import com.tfg.apirest.entity.PropiedadId;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.internal.Function;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class DatosCrearFarmacoDTOToFarmacoPropiedad implements Function<DatosCrearFarmacoDTO, Set<Propiedad>> {
    /** Constante Dosis Máxima */
    private static final String DOS_MAX = "DOS_MAX";
    /** Constante Presentación comercial */
    private static final String PRE = "PRE";

    @Override
    public Set<Propiedad> apply(DatosCrearFarmacoDTO datos) {

        var propiedades = new HashSet<Propiedad>();

        // Dosis máxima
        var propiedadDOS = new PropiedadId();
        var dosisMaximaDatos = datos.getPropiedades().getDosisMaxima();
        propiedadDOS.setCdPropiedad(DOS_MAX);
        propiedadDOS.setValor(dosisMaximaDatos.getValor());
        propiedadDOS.setUnidad(dosisMaximaDatos.getUnidad());
        propiedades.add(Propiedad.builder().id(propiedadDOS).build());

        // Presentacion comercial
        var propiedadPRE = new PropiedadId();
        var presentacionesDatos = datos.getPropiedades().getPresentaciones();
        presentacionesDatos.forEach( pre -> {
            propiedadPRE.setCdPropiedad(PRE);
            propiedadPRE.setValor(pre.getValor());
            propiedadPRE.setUnidad(pre.getUnidad());
            propiedades.add(Propiedad.builder().id(propiedadPRE).build());
        });

        return propiedades;
    }
}
