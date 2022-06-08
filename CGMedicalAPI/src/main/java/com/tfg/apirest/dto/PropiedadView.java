package com.tfg.apirest.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"id", "valor", "unidad"})
public class PropiedadView  implements Serializable {
    /** Id de la propiedad */
    private UUID id;
    /** Valor numérico de la propiedad */
    private Double valor;
    /** Unidades de medida de la propiedad */
    private String unidad;
}
