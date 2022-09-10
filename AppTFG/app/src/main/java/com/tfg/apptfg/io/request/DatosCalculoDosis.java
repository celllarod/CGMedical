package com.tfg.apptfg.io.request;

import java.util.List;

public class DatosCalculoDosis {

    private PropiedadSimple dosisActualFd;
    private PropiedadSimple dosisNuevaFd;
    private PropiedadSimple dosisActualFs;
    private PropiedadSimple dosisNuevaFs;

    public DatosCalculoDosis() {
    }

    public DatosCalculoDosis(PropiedadSimple dosisActualFd, PropiedadSimple dosisNuevaFd, PropiedadSimple dosisActualFs, PropiedadSimple dosisNuevaFs) {
        this.dosisActualFd = dosisActualFd;
        this.dosisNuevaFd = dosisNuevaFd;
        this.dosisActualFs = dosisActualFs;
        this.dosisNuevaFs = dosisNuevaFs;
    }

    public PropiedadSimple getDosisActualFd() {
        return dosisActualFd;
    }

    public void setDosisActualFd(PropiedadSimple dosisActualFd) {
        this.dosisActualFd = dosisActualFd;
    }

    public PropiedadSimple getDosisNuevaFd() {
        return dosisNuevaFd;
    }

    public void setDosisNuevaFd(PropiedadSimple dosisNuevaFd) {
        this.dosisNuevaFd = dosisNuevaFd;
    }

    public PropiedadSimple getDosisActualFs() {
        return dosisActualFs;
    }

    public void setDosisActualFs(PropiedadSimple dosisActualFs) {
        this.dosisActualFs = dosisActualFs;
    }

    public PropiedadSimple getDosisNuevaFs() {
        return dosisNuevaFs;
    }

    public void setDosisNuevaFs(PropiedadSimple dosisNuevaFs) {
        this.dosisNuevaFs = dosisNuevaFs;
    }
}
