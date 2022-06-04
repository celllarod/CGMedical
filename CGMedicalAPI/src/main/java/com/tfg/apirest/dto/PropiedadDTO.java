package com.tfg.apirest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PropiedadDTO {
    /** Valor numérico de la propiedad */
    @JsonProperty("valor")
    @NotNull(message = "El parámetro 'valor' no puede ir vacío.")
    Double valor;
    /** Unidades de medida de la propiedad */
    @JsonProperty("unidad")
    @NotNull(message = "El parámetro 'unidad' no puede ir vacío.")
    String unidad;
}
