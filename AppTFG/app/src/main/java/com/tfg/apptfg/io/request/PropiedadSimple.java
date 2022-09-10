package com.tfg.apptfg.io.request;

public class PropiedadSimple {

    /** Valor numérico de la propiedad */
    private Double valor;
    /** Unidades de medida de la propiedad */
    private String unidad;

    public PropiedadSimple(Double valor, String unidad) {
        this.valor = valor;
        this.unidad = unidad;
    }

    public PropiedadSimple() {
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

}
