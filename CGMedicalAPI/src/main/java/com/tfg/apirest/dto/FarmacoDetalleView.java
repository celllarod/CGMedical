package com.tfg.apirest.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "nombre", "propiedades"})
public class FarmacoDetalleView {

    /** UUID */
    private UUID id;
    /** Nombre del fármaco */
    private String nombre;
    /** Propiedades del fármaco*/
    private PropiedadesView propiedades;
}
