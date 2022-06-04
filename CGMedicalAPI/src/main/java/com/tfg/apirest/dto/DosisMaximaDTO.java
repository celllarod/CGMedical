package com.tfg.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class DosisMaximaDTO {
    /** Valor numérico de la propiedad */
    @JsonProperty("valor")
    @NotNull(message = "El campo 'propiedades.dosisMaxima.valor' no puede ir vacío.")
    Double valor;
    /** Unidades de medida de la propiedad */
    @NotNull(message = "El campo 'propiedades.dosisMaxima.unidad' no puede ir vacío.")
    @Pattern(regexp = "[mu]g\\/(dia)" , message = "El campo 'propiedades.dosisMaxima.unidad' tiene un valor no soportado.")
    String unidad;
}
