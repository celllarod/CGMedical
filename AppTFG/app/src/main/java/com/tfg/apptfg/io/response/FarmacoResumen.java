package com.tfg.apptfg.io.response;

import java.util.UUID;

public class FarmacoResumen {

    /** UUID del fármaco */
    private UUID id;
    /** Nombre del fármaco */
    private String nombre;
    /** Dosis máxima del fármaco */
    private Propiedad dosisMaxima;

    public FarmacoResumen(UUID id, String nombre, Propiedad dosisMaxima) {
        this.id = id;
        this.nombre = nombre;
        this.dosisMaxima = dosisMaxima;
    }

    public FarmacoResumen() {
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

    public Propiedad getDosisMaxima() {
        return dosisMaxima;
    }

    public void setDosisMaxima(Propiedad dosisMaxima) {
        this.dosisMaxima = dosisMaxima;
    }
}
