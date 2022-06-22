package com.tfg.apptfg.io.response;

import java.util.UUID;

public class Propiedad {

    /** Id de la propiedad */
    private UUID id;
    /** Valor numérico de la propiedad */
    private Double valor;
    /** Unidades de medida de la propiedad */
    private String unidad;

    public Propiedad(UUID id, Double valor, String unidad) {
        this.id = id;
        this.valor = valor;
        this.unidad = unidad;
    }

    public Propiedad() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
