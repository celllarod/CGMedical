package com.tfg.apptfg.io.request;

public class DatosFarmacoCrear {

    /** Nombre del fármaco */
    private String nombre;
    /** Propiedades del fármaco */
    private Propiedades propiedades;

    public DatosFarmacoCrear(String nombre, Propiedades propiedades) {
        this.nombre = nombre;
        this.propiedades = propiedades;
    }

    public DatosFarmacoCrear() {
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
