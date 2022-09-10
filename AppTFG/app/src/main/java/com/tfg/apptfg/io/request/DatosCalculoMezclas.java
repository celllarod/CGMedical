package com.tfg.apptfg.io.request;

import java.util.List;

public class DatosCalculoMezclas {

    /** Volumen de la bomba de infusión */
    private PropiedadSimple volumenBomba;

    /** Fármaco dominante */
    private FarmacoDominante farmacoDominante;

    /** Listado de fármacos secundarios*/
    private List<FarmacoSecundario> farmacosSecundarios;

    public DatosCalculoMezclas() {
    }

    public PropiedadSimple getVolumenBomba() {
        return volumenBomba;
    }

    public void setVolumenBomba(PropiedadSimple volumenBomba) {
        this.volumenBomba = volumenBomba;
    }

    public FarmacoDominante getFarmacoDominante() {
        return farmacoDominante;
    }

    public void setFarmacoDominante(FarmacoDominante farmacoDominante) {
        this.farmacoDominante = farmacoDominante;
    }

    public List<FarmacoSecundario> getFarmacosSecundarios() {
        return farmacosSecundarios;
    }

    public void setFarmacosSecundarios(List<FarmacoSecundario> farmacosSecundarios) {
        this.farmacosSecundarios = farmacosSecundarios;
    }

    public DatosCalculoMezclas(PropiedadSimple volumenBomba, FarmacoDominante farmacoDominante, List<FarmacoSecundario> farmacosSecundarios) {
        this.volumenBomba = volumenBomba;
        this.farmacoDominante = farmacoDominante;
        this.farmacosSecundarios = farmacosSecundarios;
    }
}
