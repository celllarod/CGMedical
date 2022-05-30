package com.tfg.apirest.dto;

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
public class FarmacoView {

    private UUID id;
    private String nombre;
    private List<PropiedadView> propiedades;
    private String hospital;
}
