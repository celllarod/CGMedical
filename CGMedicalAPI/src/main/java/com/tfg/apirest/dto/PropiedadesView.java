package com.tfg.apirest.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"dosisMaxima", "presentaciones", "concentraciones"})
public class PropiedadesView {
    /** Dosis máxima del fármaco */
    PropiedadView dosisMaxima;
    /** Listado de presentaciones comerciales del fármaco */
    List<PropiedadView> presentaciones;
    /** Listado de concentraciones del fármaco */
    List<PropiedadView> concentraciones;

}
