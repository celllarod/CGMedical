package com.tfg.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tfg.apirest.validation.group.Crear;
import com.tfg.apirest.validation.group.Modificar;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class PropiedadesDTO {
    /** Dosis máxima del fármaco */
    @JsonProperty("dosisMaxima")
    @Valid
    @NotNull(message = "El campo 'dosisMaxima' no puede ir vacío.", groups={Modificar.class, Crear.class})
    private DosisMaximaDTO dosisMaxima;
    /** Listado de presentaciones comerciales del fármaco */
    @JsonProperty("presentaciones")
    @Valid
    @NotNull(message = "El campo 'presentaciones' no puede ir vacío.", groups={Modificar.class, Crear.class})
    Set<PresentacionDTO> presentaciones;
}
