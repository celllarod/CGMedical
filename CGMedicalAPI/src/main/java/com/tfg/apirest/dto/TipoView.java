package com.tfg.apirest.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"cogigo", "descripcion"})
public class TipoView {
    /** Código del tipo de la propiedad */
    String codigo;
    /** Descripción del tipo de la propiedad */
    String descripcion;
}
