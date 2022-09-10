package com.tfg.apptfg.io.response;

import com.tfg.apptfg.io.request.Propiedades;

import java.util.UUID;

public class FarmacoDetalle {

    /** UUID */
    private UUID id;
    /** Nombre del fármaco */
    private String nombre;
    /** Propiedades del fármaco */
    private Propiedades propiedades;

    public FarmacoDetalle(UUID id, String nombre, Propiedades propiedades) {
        this.id = id;
        this.nombre = nombre;
        this.propiedades = propiedades;
    }

    public FarmacoDetalle() {
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

    public Propiedades getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(Propiedades propiedades) {
        this.propiedades = propiedades;
    }
}
