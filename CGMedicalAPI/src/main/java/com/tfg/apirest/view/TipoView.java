package com.tfg.apirest.view;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"cogigo", "descripcion"})
public class TipoView  implements Serializable{
    /** Código del tipo de la propiedad */
    String codigo;
    /** Descripción del tipo de la propiedad */
    String descripcion;
}
