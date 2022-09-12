package com.tfg.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tfg.apirest.validation.group.Crear;
import com.tfg.apirest.validation.group.Modificar;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Getter
@Setter
public class VolumenDTO {

    /** Valor numérico de la propiedad */
    @JsonProperty("valor")
    @NotNull(message = "El campo 'valor' es obligatorio.")
    Double valor;
    /** Unidad de medida de la propiedad */
    @NotBlank(message = "El campo 'unidad' no puede ir vacío.")
    @NotNull(message = "El campo 'unidad' es obligatorio.")
    @Pattern(regexp = "mL" , message = "El campo 'unidad' tiene un valor no soportado.")
    String unidad;
}
