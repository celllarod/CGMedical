package com.tfg.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tfg.apirest.validation.group.Crear;
import com.tfg.apirest.validation.group.Modificar;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Getter
@Setter
public class DosisMaximaDTO {
    /** Id de la propiedad */
    @JsonProperty("id")
    @Null(message = "El campo 'id' no se debe indicar, se asignará automáticamente.", groups = {Crear.class})
    private UUID id;
    /** Valor numérico de la propiedad */
    @JsonProperty("valor")
    @NotNull(message = "El campo 'valor' no puede ir vacío.",  groups={Modificar.class, Crear.class})
    Double valor;
    /** Unidades de medida de la propiedad */
    @NotNull(message = "El campo 'unidad' no puede ir vacío.", groups={Modificar.class, Crear.class})
    @Pattern(regexp = "[mu]g\\/(dia)" , message = "El campo 'unidad' tiene un valor no soportado.",  groups={Modificar.class, Crear.class})
    String unidad;
}
