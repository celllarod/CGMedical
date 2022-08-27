package com.tfg.apptfg.ui.mezclas;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tfg.apptfg.io.request.DatosCalculoMezclas;
import com.tfg.apptfg.io.request.FarmacoDominante;
import com.tfg.apptfg.io.request.FarmacoSecundario;
import com.tfg.apptfg.io.request.PropiedadSimple;

import java.util.List;
import java.util.Objects;

public class MezclasViewModel extends ViewModel {
    private  MutableLiveData<PropiedadSimple> volumen;
    private  MutableLiveData<FarmacoDominante> farmacoDominante;
    private  MutableLiveData<List<FarmacoSecundario>> farmacosSecundariosList;

    public MezclasViewModel() {

    }

    public DatosCalculoMezclas getDatos() {
        DatosCalculoMezclas datosCalculoMezclas = new DatosCalculoMezclas();
        if (isValid()) {
            datosCalculoMezclas.setVolumenBomba(volumen.getValue());
            datosCalculoMezclas.setFarmacoDominante(farmacoDominante.getValue());
            datosCalculoMezclas.setFarmacosSecundarios(farmacosSecundariosList.getValue());
        }
        return datosCalculoMezclas;
    }

    public void setVolumenBomba(String volumenBomba) {
        String[] volumenSplit = volumenBomba.split(" ");
        volumen = new MutableLiveData<>();
        volumen.setValue( new PropiedadSimple(Double.valueOf(volumenSplit[0]), volumenSplit[1]));
    }

    public void setFarmacoDominante(String nombre, PropiedadSimple concentracion, PropiedadSimple presentacion, PropiedadSimple dosis){
        farmacoDominante = new MutableLiveData<>();
        farmacoDominante.setValue(new FarmacoDominante(nombre, concentracion, presentacion, dosis));
    }

    public void setFarmacosSecundariosList(List<FarmacoSecundario> fsList){
        farmacosSecundariosList = new MutableLiveData<>();
        farmacosSecundariosList.setValue(fsList);
    }

    public MutableLiveData<List<FarmacoSecundario>> getFarmacosSecundariosList() {
        return farmacosSecundariosList;
    }

//    public MutableLiveData<PropiedadSimple> getVolumen() {
//        return volumen;
//    }

//    public MutableLiveData<FarmacoDominante> getFarmacoDominante() {
//        return farmacoDominante;
//    }

    public boolean isEmptyVolumen(){
        return Objects.isNull(volumen);
    }

    public boolean isEmptyFarmacoDominante(){
        return Objects.isNull(farmacoDominante);
    }

    public boolean isValidFarmacoDominante(){
        boolean result;
        if (!isEmptyFarmacoDominante()) {
            result = !Objects.isNull(Objects.requireNonNull(farmacoDominante.getValue()).getNombre());
            result = !Objects.isNull(farmacoDominante.getValue().getDosis()) && result;
            result = !Objects.isNull(farmacoDominante.getValue().getConcentracion()) && result;
            result = !Objects.isNull(farmacoDominante.getValue().getPresentacion()) && result;
        } else {
            result = false;
        }
        return result;
    }

    public boolean isValidFarmacosSecundarios(){
        return !Objects.isNull(farmacosSecundariosList);
    }

    public boolean isValid(){
        boolean result = !isEmptyVolumen();
        result = isValidFarmacoDominante() && result;
        return !Objects.isNull(farmacosSecundariosList.getValue()) && result;

    }
}