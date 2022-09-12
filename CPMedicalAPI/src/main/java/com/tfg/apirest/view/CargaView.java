package com.tfg.apirest.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"nombre", "carga"})
public class CargaView  implements Serializable {
    /** Nombre del fármaco */
    private String nombre;
    /** Carga del fármado en la bomba (valor unidad) */
    private String carga;
}
