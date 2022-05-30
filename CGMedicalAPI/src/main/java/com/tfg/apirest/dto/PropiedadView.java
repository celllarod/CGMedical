package com.tfg.apirest.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropiedadView {

    TipoView tipo;
    Double valor;
    String unidad;

}
