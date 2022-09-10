package com.tfg.apirest.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"dosisMaxima", "presentaciones"})
public class PropiedadesView  implements Serializable {
    /** Dosis máxima del fármaco */
    PropiedadView dosisMaxima;
    /** Listado de presentaciones comerciales del fármaco */
    List<PropiedadView> presentaciones;
}
