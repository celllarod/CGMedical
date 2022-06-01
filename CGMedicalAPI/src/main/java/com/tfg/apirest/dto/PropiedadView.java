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
@JsonPropertyOrder({"valor", "unidad"})
public class PropiedadView {
    /** Valor numérico de la propiedad */
    Double valor;
    /** Unidades de medida de la propiedad */
    String unidad;

}
