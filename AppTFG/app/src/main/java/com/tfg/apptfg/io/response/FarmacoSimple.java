package com.tfg.apptfg.io.response;

import com.tfg.apptfg.io.request.Propiedades;

import java.util.UUID;

public class FarmacoSimple {

    /** UUID */
    private UUID id;
    /** Nombre del fármaco */
    private String nombre;

    public FarmacoSimple(UUID id, String nombre, Propiedades propiedades) {
        this.id = id;
        this.nombre = nombre;
    }

    public FarmacoSimple() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
