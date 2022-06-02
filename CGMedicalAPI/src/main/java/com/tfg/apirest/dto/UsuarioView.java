package com.tfg.apirest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

//TODO: eliminar o reaprovechar
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioView  implements Serializable {

    private String nombre;
    private String apellido1;
}
