package com.tfg.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tfg.apirest.validation.group.Crear;
import com.tfg.apirest.validation.group.Modificar;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class ConcentracionDTO {
    /** Valor numérico de la propiedad */
    @JsonProperty("valor")
    @NotNull(message = "El campo 'valor' es obligatorio.")
    Double valor;
    /** Unidades de medida de la propiedad */
    @NotBlank(message = "El campo 'unidad' no puede ir vacío.")
    @NotNull(message = "El campo 'unidad' es obligatorio.")
    @Pattern(regexp = "[mu]g/(mL)" , message = "El campo 'unidad' tiene un valor no soportado.")
    String unidad;
}
