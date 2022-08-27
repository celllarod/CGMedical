package com.tfg.apptfg.ui.mezclas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tfg.apptfg.io.request.DatosCalculoMezclas;
import com.tfg.apptfg.io.request.FarmacoDominante;
import com.tfg.apptfg.io.request.FarmacoSecundario;
import com.tfg.apptfg.io.request.PropiedadSimple;

import java.util.List;
import java.util.Objects;

public class MezclasViewModel extends ViewModel {
    private  MutableLiveData<DatosCalculoMezclas> datos = new MutableLiveData<>();
    private  MutableLiveData<PropiedadSimple> volumen;
    private  MutableLiveData<FarmacoDominante> farmacoDominante;
    private  MutableLiveData<List<FarmacoSecundario>> farmacosSecundariosList;
    private  MutableLiveData<Boolean> isValidFd = new MutableLiveData<>(true);
    private  MutableLiveData<Boolean> isEmptyFd = new MutableLiveData<>(true);

    public MezclasViewModel() {

    }

    public LiveData<DatosCalculoMezclas> getDatos() {
        return datos;
    }

    public void setIsValidFd(Boolean fdValidation) {
        isValidFd.setValue(fdValidation);
    }

    public void setIsEmptyFd(Boolean fdEmpty) {
        isEmptyFd .setValue(fdEmpty);
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

    public boolean isEmptyVolumen(){
        return Objects.isNull(volumen);
    }

    public boolean isEmptyFarmacoDominante(){
        return isEmptyFd.getValue();
    }

    public boolean isValidFarmacoDominante(){
        return isValidFd.getValue();
    }
}