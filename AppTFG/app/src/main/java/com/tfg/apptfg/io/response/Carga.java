package com.tfg.apptfg.io.response;

public class Carga {

    /** Nombre del fármaco */
    private String nombre;
    /** Carga del fármado en la bomba (valor unidad) */
    private String carga;

    public Carga(String nombre, String carga) {
        this.nombre = nombre;
        this.carga = carga;
    }

    public Carga() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }
}
