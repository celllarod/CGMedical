package com.tfg.apptfg.io.request;

public class FarmacoDominante {
    /* Nombre */
    private String nombre;
    /** Concentración */
    PropiedadSimple concentracion;
    /** Presentación */
    PropiedadSimple presentacion;
    /** Dosis */
    PropiedadSimple dosis;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public PropiedadSimple getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(PropiedadSimple concentracion) {
        this.concentracion = concentracion;
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

    public FarmacoDominante(String nombre, PropiedadSimple concentracion, PropiedadSimple presentacion, PropiedadSimple dosis) {
        this.nombre = nombre;
        this.concentracion = concentracion;
        this.presentacion = presentacion;
        this.dosis = dosis;
    }

    public FarmacoDominante() {
    }
}
