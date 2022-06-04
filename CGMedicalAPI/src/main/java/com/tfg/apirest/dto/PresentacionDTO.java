package com.tfg.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@EqualsAndHashCode
public class PresentacionDTO {
    /** Valor numérico de la propiedad */
    @JsonProperty("valor")
    @NotNull(message = "El campo 'propiedades.presentacion.valor' no puede ir vacío.")
    Double valor;
    /** Unidades de medida de la propiedad */
    @NotNull(message = "El campo 'propiedades.presentacion.unidad' no puede ir vacío.")
    @Pattern(regexp = "[mu]g\\/(mL)" , message = "El campo 'propiedades.presentacion.unidad' tiene un valor no soportado.")
    String unidad;
}
