package com.tfg.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FarmacoSecundarioDTO {
    /** Dosis máxima del fármaco */
    @JsonProperty("nombre")
    @NotBlank(message = "El campo 'nombre' no puede ir vacío.")
    @NotNull(message = "El campo 'nombre' es obligatorio.")
    private String nombre;
    /** Concentración */
    @JsonProperty("presentacion")
    @Valid
    @NotNull(message = "El campo 'presentacion' es obligatorio.")
    ConcentracionDTO presentacion;
    /** Dosis */
    @JsonProperty("dosis")
    @Valid
    @NotNull(message = "El campo 'dosis' es obligatorio.")
    DosisDTO dosis;
}
