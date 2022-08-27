package com.tfg.apptfg.ui.mezclas.step;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tfg.apptfg.io.request.PropiedadSimple;

public class VolumenStepViewModel extends ViewModel {

    private MutableLiveData<PropiedadSimple> volumen;

    public VolumenStepViewModel(){  }

    public MutableLiveData<PropiedadSimple> getVolumenBomba(){
        return volumen;
    }

    public void setVolumenBomba(String volumenBomba) {

        String[] volumenSplit = volumenBomba.split(" ");
        volumen = new MutableLiveData<>();
        volumen.setValue(new PropiedadSimple(Double.valueOf(volumenSplit[0]), volumenSplit[1]));
    }
}