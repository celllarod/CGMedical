package com.tfg.apptfg.ui.catalogo;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CatalogoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CatalogoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Catalogo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
