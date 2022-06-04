package com.tfg.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PropiedadesDTO {
    /** Dosis máxima del fármaco */
    @JsonProperty("dosisMaxima")
    @Valid
    @NotNull(message = "El campo 'propiedades.dosisMaxima' no puede ir vacío.")
    private DosisMaximaDTO dosisMaxima;
    /** Listado de presentaciones comerciales del fármaco */
    @JsonProperty("presentaciones")
    @Valid
    @NotNull(message = "El campo 'propiedades.presentaciones' no puede ir vacío.")
    Set<PresentacionDTO> presentaciones;
}
