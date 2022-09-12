package com.tfg.apirest.function;

import com.tfg.apirest.dto.PropiedadesDTO;
import com.tfg.apirest.entity.Propiedad;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.internal.Function;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class PropiedadesDTOToPropiedadesSetFunction implements Function<PropiedadesDTO, Set<Propiedad>> {
    /** Constante Dosis Máxima */
    private static final String DOS_MAX = "DOS_MAX";
    /** Constante Presentación comercial */
    private static final String PRE = "PRE";

    @Override
    public Set<Propiedad> apply(PropiedadesDTO datos) {

        var propiedades = new HashSet<Propiedad>();

        // Dosis máxima
        var dosisMaximaDatos = datos.getDosisMaxima();

        var propiedadDOS = Propiedad.builder() //
                                    .cdPropiedad(DOS_MAX)//
                                    .id(dosisMaximaDatos.getId()) //
                                    .valor(dosisMaximaDatos.getValor()) //
                                    .unidad(dosisMaximaDatos.getUnidad()) //
                                    .build();
        propiedades.add(propiedadDOS);


        // Presentacion comercial
        var presentacionesDatos = datos.getPresentaciones();

        presentacionesDatos.forEach(pre -> {
            var propiedadPRE = Propiedad.builder() //
                                        .cdPropiedad(PRE)//
                                        .id(pre.getId()) //
                                        .valor(pre.getValor()) //
                                        .unidad(pre.getUnidad())//
                                        .build();
            propiedades.add(propiedadPRE);
        });

        return propiedades;
    }
}
