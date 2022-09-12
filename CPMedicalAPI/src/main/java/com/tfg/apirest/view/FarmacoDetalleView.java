package com.tfg.apirest.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "nombre", "propiedades"})
public class FarmacoDetalleView implements Serializable {

    /** UUID */
    private UUID id;
    /** Nombre del fármaco */
    private String nombre;
    /** Propiedades del fármaco*/
    private PropiedadesView propiedades;
}
