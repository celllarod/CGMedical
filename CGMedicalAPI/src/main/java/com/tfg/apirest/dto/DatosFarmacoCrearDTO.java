package com.tfg.apirest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.tfg.apirest.validation.group.Crear;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class DatosFarmacoCrearDTO {

    /** Nombre del fármaco */
    @JsonProperty("nombre")
    @NotNull(message = "El parámetro 'nombre' no puede ir vacío.", groups=Crear.class )
    @Size(max = 100, message=  "El campo 'nombre' ha superado el máximo de 100 caracteres.", groups=Crear.class)
    private String nombre;

    /** Propiedades del fármaco */
    @JsonProperty("propiedades")
    @Valid
    @NotNull(message = "El parámetro 'propiedades' no puede ir vacío",  groups=Crear.class)
    private PropiedadesDTO propiedades;
}
