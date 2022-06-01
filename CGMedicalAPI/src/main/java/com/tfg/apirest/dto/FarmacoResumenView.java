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
@JsonPropertyOrder({"id", "nombre", "dosisMaxima"})
public class FarmacoResumenView {
    /** UUID del fármaco */
    private UUID id;
    /** Nombre del fármaco */
    private String nombre;
    /** Dosis máxima del fármaco */
    private PropiedadView dosisMaxima;
}
