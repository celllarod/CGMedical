package com.tfg.apptfg.io.request;

import com.tfg.apptfg.io.response.Propiedad;

import java.util.Set;

public class Propiedades {
    /** Dosis máxima del fármaco */
    private Propiedad dosisMaxima;
    /** Listado de presentaciones comerciales del fármaco */
    Set<Propiedad> presentaciones;

    public Propiedades(Propiedad dosisMaxima, Set<Propiedad> presentaciones) {
        this.dosisMaxima = dosisMaxima;
        this.presentaciones = presentaciones;
    }

    public Propiedades() {
    }

    public Propiedad getDosisMaxima() {
        return dosisMaxima;
    }

    public void setDosisMaxima(Propiedad dosisMaxima) {
        this.dosisMaxima = dosisMaxima;
    }

    public Set<Propiedad> getPresentaciones() {
        return presentaciones;
    }

    public void setPresentaciones(Set<Propiedad> presentaciones) {
        this.presentaciones = presentaciones;
    }
}
