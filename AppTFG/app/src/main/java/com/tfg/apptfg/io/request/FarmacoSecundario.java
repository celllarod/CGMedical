package com.tfg.apptfg.io.request;

public class FarmacoSecundario {
    /** Nombre */
    private String nombre;
    /** Concentración */
    PropiedadSimple presentacion;
    /** Dosis */
    PropiedadSimple dosis;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public PropiedadSimple getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(PropiedadSimple presentacion) {
        this.presentacion = presentacion;
    }

    public PropiedadSimple getDosis() {
        return dosis;
    }

    public void setDosis(PropiedadSimple dosis) {
        this.dosis = dosis;
    }

    public FarmacoSecundario(String nombre, PropiedadSimple presentacion, PropiedadSimple dosis) {
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.dosis = dosis;
    }

    public FarmacoSecundario() {
    }
}
