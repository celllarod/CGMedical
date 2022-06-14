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
public class PresentacionDTO {
    /** Id de la propiedad */
    @JsonProperty("id")
    @Null(message = "El campo 'id' no se debe indicar, se asignará automáticamente.", groups = {Crear.class})
    private UUID id;
    /** Valor numérico de la propiedad */
    @JsonProperty("valor")
    @NotBlank(message = "El campo 'valor' no puede ir vacío.", groups={Modificar.class, Crear.class})
    @NotNull(message = "El campo 'valor' es obligatorio.", groups={Modificar.class, Crear.class})
    Double valor;
    /** Unidades de medida de la propiedad */
    @NotBlank(message = "El campo 'unidad' no puede ir vacío.", groups={Modificar.class, Crear.class})
    @NotNull(message = "El campo 'unidad' es obligatorio.", groups={Modificar.class, Crear.class})
    @Pattern(regexp = "[mu]g/(mL)" , message = "El campo 'propiedades.presentacion.unidad' tiene un valor no soportado.",  groups={Modificar.class, Crear.class})
    String unidad;
}
