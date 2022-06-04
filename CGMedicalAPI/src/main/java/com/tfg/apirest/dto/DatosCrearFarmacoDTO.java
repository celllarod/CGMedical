package com.tfg.apirest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class DatosCrearFarmacoDTO {

    /** Nombre del fármaco */
    @JsonProperty("nombre")
    @NotBlank(message = "El campo 'nombre' no puede ir vacío." )
    @Size(max = 100, message=  "El campo 'nombre' ha superado el máximo de 100 caracteres.")
    private String nombre;

    /** Propiedades del fármaco */
    @JsonProperty("propiedades")
    @Valid
    @NotNull(message = "El parámetro 'propiedades' no puede ir vacío")
    private PropiedadesDTO propiedades;
}
