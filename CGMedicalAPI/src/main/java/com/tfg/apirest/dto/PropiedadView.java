package com.tfg.apirest.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"valor", "unidad"})
public class PropiedadView  implements Serializable {
    /** Valor numérico de la propiedad */
    Double valor;
    /** Unidades de medida de la propiedad */
    String unidad;
}
